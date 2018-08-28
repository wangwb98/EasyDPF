# EasyDPF
The easiest DPF(Digital Picture Frame).

## Purpose

This is targeted to be used by myself on my Android based TV. I like to see my pictures (from my NAS server) on the TV screen, to use it as a very big DPF (Digital Picture Frame).
EasyPDF can meet my requirement and with simplest steps to use.

It scans one folder (URL like smb://192.168.0.2/photo) and display all the pictures inside that folder (and sub-folders).

## Usage

By default, it scans smb://192.168.0.2/photo. and start slides show of the pictures. Information of the picture will be shown on the top left corner.
Remote controler's DPAD can be used like:
* LEFT/RIGHT: prev or next picture in the list
* UP: trigger the rescan of the NAS folder to add new pictures
* DOWN: enter settings, with below options
	* set the IP address and folder name
	* set to the NNNth picture in the picture list, as a quick jump

## To do

1. LEFT/RIGHT key long press should be able to do the quick jump in the picture list
2. add a option to Clean Rescan of the NAS folder (means it will forget all the previous pictures and rescan)
3. Add a welcome screen to show how to use this app.
4. add the options to sort pictures by time, by folder.

## License

This project is distributed under the GNU General Public License Version 3 or greater.
