from socket import AF_INET, SOCK_STREAM, socket


server_port = 12000

serverSocket = socket(AF_INET, SOCK_STREAM)
serverSocket.bind(('', server_port))
serverSocket.listen(1)
print("Server start to listening")
while True:
    connetSocket, ipAdd = serverSocket.accept()
    meg = connetSocket.recv(2048)
    print("meg from " + str(ipAdd[0]) + ":" + str(ipAdd[1]) + ": " + meg.decode())
    modifiedMeg = meg.decode().upper()
    connetSocket.send(modifiedMeg.encode())
    connetSocket.close()