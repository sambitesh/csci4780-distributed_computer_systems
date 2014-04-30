#Programming Project 1: Simple FTP Client and Server

The aim of this project is to introduce you to the basics of the client-server model of distributed systems. In this project, you will be designing and implementing simplified versions of FTP client and server. The client executable will be called “myftp” and the server executable will be called “myftpserver”. You are only required to implement the following FTP commands. The syntax of the command is indicated in the parenthesis.

1. get (get <remote_filename>) -- Copy file with the name <remote_filename> from remote directory to local directory.
2. put (put <local_filename>) -- Copy file with the name <local_filename> from local directory to remote directory.
3. delete (delete <remote_filename>) – Delete the file with the name <remote_filename> from the remote directory.
4. ls (ls) -- List the files and subdirectories in the remote directory.
5. cd (cd <remote_direcotry_name> or cd ..) – Change to the <remote_direcotry_name > on the remote machine or change to the parent directory of the current directory
6. mkdir (mkdir <remote_directory_name>) – Create directory named <remote_direcotry_name> as the sub-directory of the current working directory on the remote machine.
7. pwd (pwd) – Print the current working directory on the remote machine.
8. quit (quit) – End the FTP session.


The workings of the client and server are explained below.
