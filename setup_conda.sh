#!/bin/bash

# Define the Miniconda installation directory and filename
miniconda_dir="$HOME/miniconda"
miniconda_installer="Miniconda3-latest-Linux-x86_64.sh"

# Download the Miniconda installer script
wget https://repo.anaconda.com/miniconda/$miniconda_installer -P $HOME

# Run the Miniconda installer
bash $HOME/$miniconda_installer -b -p $miniconda_dir

# Add Miniconda binaries to the PATH
echo 'export PATH="'"$miniconda_dir/bin"':$PATH"' >> $HOME/.bashrc

# Initialize Miniconda (activate the base environment)
source $miniconda_dir/etc/profile.d/conda.sh
conda init bash

# Remove the installer script
rm $HOME/$miniconda_installer

echo "Miniconda has been installed and initialized. Please restart your shell or run 'source ~/.bashrc' to use it."
