#!/usr/bin/env bash
#
# Python Version Manager Setup Script
# 
# This script installs and configures Python versions via Homebrew, handling
# macOS version compatibility issues. It automatically detects the appropriate
# Python version based on your macOS version and system architecture.
#
# Usage: ./on_pvm.sh [python_version]
#        ./on_pvm.sh 3.11    # Install specific version
#        ./on_pvm.sh         # Auto-detect compatible version
#
# Exit codes:
#   0 - Success
#   1 - Error (with descriptive message)
#
# Author: Jaehoon Song
# License: MIT

set -euo pipefail

# Source system detection if available
if [[ -f "./system_detect.sh" ]]; then
    source "./system_detect.sh"
    detect_system
fi

# Colors for output (only if terminal supports it)
if [[ -t 1 ]]; then
    readonly RED='\033[0;31m'
    readonly GREEN='\033[0;32m'
    readonly YELLOW='\033[1;33m'
    readonly BLUE='\033[0;34m'
    readonly NC='\033[0m' # No Color
else
    readonly RED=''
    readonly GREEN=''
    readonly YELLOW=''
    readonly BLUE=''
    readonly NC=''
fi

# Logging functions
log_info() {
    echo -e "${BLUE}[INFO]${NC} $*"
}

log_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $*"
}

log_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $*"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $*" >&2
}

# Error handling
error_exit() {
    log_error "$1"
    exit 1
}

# Check if Homebrew is available
check_homebrew() {
    if ! command -v brew >/dev/null 2>&1; then
        error_exit "Homebrew is not installed. Please run on_brew.sh first."
    fi
    log_info "Homebrew is available"
}

# Get macOS version information
get_macos_version() {
    # Use system detection if available
    if [[ -n "${MACOS_VERSION:-}" ]] && [[ -n "${MACOS_BUILD:-}" ]]; then
        echo "${MACOS_VERSION}:${MACOS_BUILD}"
    else
        # Fallback detection
        local macos_version
        local macos_build
        
        # Get macOS version (e.g., "14.7.6")
        macos_version=$(sw_vers -productVersion)
        
        # Get macOS build number (e.g., "23G93")
        macos_build=$(sw_vers -buildVersion)
        
        echo "${macos_version}:${macos_build}"
    fi
}

# Determine compatible Python version based on macOS
get_compatible_python_version() {
    local macos_info
    local macos_version
    local macos_build
    local major_version
    local minor_version
    local patch_version
    
    macos_info=$(get_macos_version)
    macos_version=$(echo "${macos_info}" | cut -d: -f1)
    macos_build=$(echo "${macos_info}" | cut -d: -f2)
    
    # Parse version components
    major_version=$(echo "${macos_version}" | cut -d. -f1)
    minor_version=$(echo "${macos_version}" | cut -d. -f2)
    patch_version=$(echo "${macos_version}" | cut -d. -f3)
    
    # Python version compatibility matrix
    # Python 3.13+ requires macOS 14.7.6+ (build 23G93+)
    # Python 3.12+ requires macOS 14.7.6+ (build 23G93+)
    # Python 3.11+ requires macOS 10.15+
    # Python 3.10+ requires macOS 10.15+
    # Python 3.9+ requires macOS 10.15+
    
    if [[ "${major_version}" -eq 14 ]]; then
        if [[ "${minor_version}" -eq 7 ]] && [[ "${patch_version}" -ge 6 ]]; then
            # Check if build number is 23G93 or higher for Python 3.12+
            if [[ "${macos_build}" == "23G93" ]] || [[ "${macos_build}" > "23G93" ]]; then
                echo "3.12"
            else
                # macOS 14.7.6 but older build - use Python 3.11
                echo "3.11"
            fi
        else
            # macOS 14.x (but < 14.7.6) - use Python 3.11
            echo "3.11"
        fi
    elif [[ "${major_version}" -ge 10 ]] && [[ "${minor_version}" -ge 15 ]]; then
        # macOS 10.15+ can use Python 3.11
        echo "3.11"
    else
        error_exit "Unsupported macOS version: ${macos_version}. This script requires macOS 10.15 or later."
    fi
}

# Install Python version
install_python() {
    local python_version="$1"
    local formula_name="python@${python_version}"
    
    log_info "Installing Python ${python_version}..."
    
    # Check if formula exists
    if ! brew info "${formula_name}" >/dev/null 2>&1; then
        error_exit "Python ${python_version} formula not found. Available versions: $(brew search python@ | grep -E 'python@[0-9]+\.[0-9]+' | sort -V | tail -5 | tr '\n' ' ')"
    fi
    
    # Install Python
    if brew install "${formula_name}"; then
        log_success "Python ${python_version} installation completed"
    else
        error_exit "Python ${python_version} installation failed"
    fi
}

# Unlink all Python versions
unlink_all_pythons() {
    log_info "Unlinking all Python versions..."
    
    local python_versions=("3.9" "3.10" "3.11" "3.12" "3.13")
    
    for version in "${python_versions[@]}"; do
        if brew list "python@${version}" >/dev/null 2>&1; then
            log_info "Unlinking python@${version}"
            brew unlink "python@${version}" 2>/dev/null || true
        fi
    done
}

# Link specific Python version
link_python() {
    local python_version="$1"
    local formula_name="python@${python_version}"
    local homebrew_prefix
    
    # Get Homebrew prefix
    if command -v brew >/dev/null 2>&1; then
        homebrew_prefix=$(brew --prefix)
    else
        homebrew_prefix="/usr/local"
    fi
    
    log_info "Linking Python ${python_version}..."
    
    if brew link --force --overwrite "${formula_name}"; then
        log_success "Python ${python_version} linked successfully"
        
        # Create python3 symlink to point to the linked version
        local python_symlink="${homebrew_prefix}/bin/python3"
        local python_version_symlink="${homebrew_prefix}/bin/python${python_version}"
        
        if [[ -f "${python_version_symlink}" ]]; then
            log_info "Creating python3 symlink to python${python_version}"
            ln -sf "${python_version_symlink}" "${python_symlink}"
            log_success "python3 symlink created"
        else
            log_warning "Could not find python${python_version} to create symlink"
            log_info "Available Python binaries:"
            ls -la "${homebrew_prefix}/bin/" | grep python || log_warning "No Python binaries found"
        fi
    else
        error_exit "Failed to link Python ${python_version}"
    fi
}

# Configure shell environment
configure_shell() {
    local python_version="$1"
    local rc_file
    local homebrew_prefix
    local export_line
    
    # Get Homebrew prefix using brew command
    if command -v brew >/dev/null 2>&1; then
        homebrew_prefix=$(brew --prefix)
        log_info "Using brew --prefix: ${homebrew_prefix}"
    else
        # Use system detection if available, otherwise fallback
        if [[ -n "${HOMEBREW_PREFIX:-}" ]]; then
            homebrew_prefix="${HOMEBREW_PREFIX}"
            log_info "Using detected Homebrew prefix: ${homebrew_prefix}"
        else
            # Fallback detection
            if [[ -d "/opt/homebrew" ]]; then
                homebrew_prefix="/opt/homebrew"
            elif [[ -d "/usr/local" ]]; then
                homebrew_prefix="/usr/local"
            else
                error_exit "Homebrew installation not found in expected locations"
            fi
            log_info "Using fallback Homebrew prefix: ${homebrew_prefix}"
        fi
    fi
    
    # For Python, we want the Homebrew bin directory first so python3 points to the right version
    export_line="export PATH=\"${homebrew_prefix}/bin:\$PATH\""
    local backup_file
    
    # Detect shell and RC file
    local current_shell
    current_shell=$(basename "${SHELL:-$(ps -p $$ -o comm=)}")
    
    case "${current_shell}" in
        zsh)
            rc_file="${HOME}/.zshrc"
            ;;
        bash)
            if [[ -f "${HOME}/.bash_profile" ]]; then
                rc_file="${HOME}/.bash_profile"
            else
                rc_file="${HOME}/.bashrc"
            fi
            ;;
        *)
            log_warning "Unsupported shell detected: ${current_shell}. Defaulting to .zshrc"
            rc_file="${HOME}/.zshrc"
            ;;
    esac
    
    log_info "Configuring shell: ${rc_file}"
    
    # Create RC file if it doesn't exist
    if [[ ! -f "${rc_file}" ]]; then
        log_info "Creating ${rc_file}"
        touch "${rc_file}"
    fi
    
    # Check if Homebrew PATH is already configured
    if grep -Fq "${homebrew_prefix}/bin" "${rc_file}" 2>/dev/null; then
        log_info "Homebrew PATH already configured in ${rc_file}"
        return 0
    fi
    
    # Create backup
    backup_file="${rc_file}.backup.$(date +%Y%m%d_%H%M%S)"
    cp "${rc_file}" "${backup_file}"
    log_info "Created backup: ${backup_file}"
    
    # Add Python configuration
    {
        echo ""
        echo "# Python ${python_version} configuration - added by on_pvm.sh on $(date '+%Y-%m-%d %H:%M:%S')"
        echo "# Homebrew prefix: ${homebrew_prefix}"
        echo "${export_line}"
        echo ""
    } >> "${rc_file}"
    
    log_success "Homebrew PATH added to ${rc_file}"
}

# Verify Python installation
verify_installation() {
    local python_version="$1"
    local rc_file
    local homebrew_prefix
    
    log_info "Verifying Python ${python_version} installation..."
    
    # Get Homebrew prefix using brew command
    if command -v brew >/dev/null 2>&1; then
        homebrew_prefix=$(brew --prefix)
        log_info "Using brew --prefix: ${homebrew_prefix}"
    else
        # Use system detection if available, otherwise fallback
        if [[ -n "${HOMEBREW_PREFIX:-}" ]]; then
            homebrew_prefix="${HOMEBREW_PREFIX}"
            log_info "Using detected Homebrew prefix: ${homebrew_prefix}"
        else
            # Fallback detection
            if [[ -d "/opt/homebrew" ]]; then
                homebrew_prefix="/opt/homebrew"
            elif [[ -d "/usr/local" ]]; then
                homebrew_prefix="/usr/local"
            else
                error_exit "Homebrew installation not found in expected locations"
            fi
            log_info "Using fallback Homebrew prefix: ${homebrew_prefix}"
        fi
    fi
    
    # Detect RC file for sourcing
    local current_shell
    current_shell=$(basename "${SHELL:-$(ps -p $$ -o comm=)}")
    
    case "${current_shell}" in
        zsh)
            rc_file="${HOME}/.zshrc"
            ;;
        bash)
            if [[ -f "${HOME}/.bash_profile" ]]; then
                rc_file="${HOME}/.bash_profile"
            else
                rc_file="${HOME}/.bashrc"
            fi
            ;;
        *)
            rc_file="${HOME}/.zshrc"
            ;;
    esac
    
    # Source the RC file to get updated PATH
    if [[ -f "${rc_file}" ]]; then
        # shellcheck source=/dev/null
        source "${rc_file}"
    fi
    
    # Check if Homebrew Python is available (version-specific symlink)
    local homebrew_python="${homebrew_prefix}/bin/python${python_version}"
    if [[ -f "${homebrew_python}" ]]; then
        log_info "Homebrew Python found at: ${homebrew_python}"
        
        # Check version of Homebrew Python
        local installed_version
        installed_version=$("${homebrew_python}" --version 2>&1 | cut -d' ' -f2 | cut -d. -f1,2)
        
        if [[ "${installed_version}" == "${python_version}" ]]; then
            log_success "Python ${python_version} is installed correctly"
            "${homebrew_python}" --version
        else
            log_warning "Expected Python ${python_version}, but found ${installed_version}"
            "${homebrew_python}" --version
        fi
    else
        log_error "Homebrew Python not found at: ${homebrew_python}"
        log_info "Available Python binaries:"
        ls -la "${homebrew_prefix}/bin/" | grep python || log_warning "No Python binaries found"
    fi
    
    # Check which python3 is being used
    if command -v python3 >/dev/null 2>&1; then
        local current_python
        current_python=$(which python3)
        log_info "Current python3 location: ${current_python}"
        
            # Check if we're using the Homebrew Python
    if [[ "${current_python}" == "${homebrew_prefix}/bin/python3" ]] || [[ "${current_python}" == "${homebrew_python}" ]]; then
        log_success "Python ${python_version} is correctly linked"
        python3 --version
        return 0
    else
        log_warning "System Python is still being used instead of Homebrew Python"
        log_info "Expected: ${homebrew_prefix}/bin/python3 or ${homebrew_python}"
        log_info "Found: ${current_python}"
        
        # Try to create the python3 symlink if it doesn't exist
        if [[ ! -f "${homebrew_prefix}/bin/python3" ]] && [[ -f "${homebrew_python}" ]]; then
            log_info "Creating missing python3 symlink..."
            ln -sf "${homebrew_python}" "${homebrew_prefix}/bin/python3"
            log_success "python3 symlink created"
        fi
        
        # Try to source the RC file and check again
        log_info "Attempting to source ${rc_file}..."
        source "${rc_file}"
        
        # Check again after sourcing
        if command -v python3 >/dev/null 2>&1; then
            current_python=$(which python3)
            log_info "After sourcing, python3 location: ${current_python}"
            
            if [[ "${current_python}" == "${homebrew_prefix}/bin/python3" ]] || [[ "${current_python}" == "${homebrew_python}" ]]; then
                log_success "Python ${python_version} is now correctly linked!"
                python3 --version
                return 0
            fi
        fi
        
        log_info "If Python is still not working, try:"
        log_info "  1. Restart your terminal"
        log_info "  2. Or run: source ~/.zshrc"
        log_info "  3. Or use: python${python_version} directly"
    fi
    else
        error_exit "python3 command not found. Please restart your terminal or run: source ${rc_file}"
    fi
}

# Show Python compatibility information
show_compatibility_info() {
    local macos_info
    local macos_version
    
    macos_info=$(get_macos_version)
    macos_version=$(echo "${macos_info}" | cut -d: -f1)
    
    echo
    log_info "Python Version Compatibility Information:"
    echo "  macOS Version: ${macos_version}"
    echo "  Python 3.13+: Requires macOS 14.7.6+ (build 23G93+)"
    echo "  Python 3.12+: Requires macOS 14.7.6+ (build 23G93+)"
    echo "  Python 3.11+: Requires macOS 10.15+"
    echo "  Python 3.10+: Requires macOS 10.15+"
    echo "  Python 3.9+:  Requires macOS 10.15+"
    echo
}

# Uninstall all Homebrew Python versions
uninstall_all_pythons() {
    log_info "Uninstalling all Homebrew Python versions..."
    local versions=("3.9" "3.10" "3.11" "3.12" "3.13")
    for v in "${versions[@]}"; do
        if brew list "python@${v}" >/dev/null 2>&1; then
            log_info "Uninstalling python@${v}..."
            brew uninstall --ignore-dependencies "python@${v}" || true
        fi
    done
}

# Check for tkinter and install python-tk if missing
check_and_install_tk() {
    local python_version="$1"
    local python_bin="/usr/local/bin/python${python_version}"

    log_info "Checking for tkinter support in Python ${python_version}..."

    if [[ -x "${python_bin}" ]]; then
        if ! "${python_bin}" -c "import tkinter" 2>/dev/null; then
            log_warning "tkinter not found for Python ${python_version}. Installing python-tk@${python_version}..."
            brew install "python-tk@${python_version}"
        else
            log_success "tkinter is available for Python ${python_version}."
        fi
    else
        log_warning "Python binary ${python_bin} not found. Skipping tkinter check."
    fi
}

# Main function
main() {
    local target_version="${1:-}"
    
    echo
    log_info "Python Version Manager Setup Script"
    log_info "Working directory: ${PWD}"
    echo "----------------------------------------------"
    
    # Check prerequisites
    check_homebrew
    
    # Get macOS version info for logging
    local macos_info
    local macos_version
    local macos_build
    macos_info=$(get_macos_version)
    macos_version=$(echo "${macos_info}" | cut -d: -f1)
    macos_build=$(echo "${macos_info}" | cut -d: -f2)
    log_info "Detected macOS version: ${macos_version} (build ${macos_build})"
    log_info "System architecture: ${SYSTEM_ARCH:-unknown}"
    log_info "Apple Silicon: ${IS_APPLE_SILICON:-unknown}"
    
    # Determine Python version
    if [[ -z "${target_version}" ]]; then
        target_version=$(get_compatible_python_version)
        
        # Log the decision based on build number
        if [[ "${macos_version}" == "14.7.6" ]]; then
            if [[ "${macos_build}" == "23G93" ]] || [[ "${macos_build}" > "23G93" ]]; then
                log_info "macOS ${macos_version} (build ${macos_build}) supports Python 3.12+"
            else
                log_warning "macOS ${macos_version} (build ${macos_build}) detected. Python 3.12+ requires build 23G93+. Using Python 3.11."
            fi
        fi
        
        log_info "Auto-detected compatible Python version: ${target_version}"
    else
        log_info "Using requested Python version: ${target_version}"
    fi
    
    # Show compatibility information
    show_compatibility_info
    
    # Uninstall all Homebrew Python versions before installing the requested one
    uninstall_all_pythons
    
    # Install and configure Python
    install_python "${target_version}"
    unlink_all_pythons
    link_python "${target_version}"
    configure_shell "${target_version}"
    verify_installation "${target_version}"
    
    # Check for tkinter and install python-tk if missing
    check_and_install_tk "${target_version}"
    
    echo "----------------------------------------------"
    log_success "Python ${target_version} setup complete!"
    log_info "You may need to restart your terminal for changes to take effect."
    log_info "To create a virtual environment: python3 -m venv myenv"
    log_info "To activate virtual environment: source myenv/bin/activate"
}

# Run main function
main "$@"
