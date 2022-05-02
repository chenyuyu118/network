
from socket import AF_INET, SOCK_DGRAM, socket

server_port = 12000

socket1 = socket(AF_INET, SOCK_DGRAM)
socket1.bind(('', server_port))
print("The server is ready to receive")
while True:
    meg, ip = socket1.recvfrom(2048)
    print("recv from " + str(ip[0]) + ":" + str(ip[1]) + " " + meg.decode())
    modifiedMeg = meg.decode().upper()
    socket1.sendto(modifiedMeg.encode(), ip)