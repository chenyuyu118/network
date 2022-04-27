

from http import client
from socket import AF_INET, SOCK_DGRAM, socket

# server_name = 'localhost'
server_name = '175.178.166.84'
server_port = 12000
socket = socket(AF_INET, SOCK_DGRAM)
meg = input("please input lowercase sentence: ")
socket.sendto(meg.encode(), (server_name, server_port))
modifiedMeg, serverAdd = socket.recvfrom(2048)
print(modifiedMeg.decode())
socket.close()