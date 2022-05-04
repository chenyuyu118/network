import base64
from json.tool import main
from pydoc import cli
from socket import *


msg = "\r\n I love computer networks!"
endmsg = "\r\n.\r\n"
# Choose a mail server (e.g. Google mail server) and call it mailserver
mailserver = "smtp.163.com"
mailport = 25

# infos you need to fill by yourself
username = '' # your smtp server's username
pwd = "" # your authorize code
mailfrom = '' # your mail from('<' is essential)
rcptto = '' # your mail to

# Create socket called clientSocket and establish a TCP connection with mailserver
#Fill in start
clientSocket = socket(AF_INET, SOCK_STREAM)
clientSocket.settimeout(10)
clientSocket.connect((mailserver, mailport))
#Fill in end
recv = clientSocket.recv(1024)
print(recv.decode())
if recv[:3] != '220':
    print( '220 reply not received from server.')

# Send HELO command and print server response.
heloCommand = 'HELO SMTP.163.com\r\n'
clientSocket.send(heloCommand.encode())
recv1 = clientSocket.recv(1024)
print(recv1.decode())
if recv1[:3] != '250':
    print('250 reply not received from server.')

#AUTH LOGIN
# send req1
loginCommand = "AUTH LOGIN\r\n"
clientSocket.send(loginCommand.encode())
recv2 = clientSocket.recv(1024)
print("S: ", recv2[:3].decode(), end=" ")
recv2 = recv2[3:].decode()
print(base64.b64decode(recv2).decode())
#send req2: username

# 需要base64编码
username = base64.b64encode(username.encode("utf-8")).decode()
print("C: ", username)
clientSocket.sendall((username + '\r\n').encode())
recv3 = clientSocket.recv(1024)
print("S: ", recv3[:3].decode(), end=" ")
recv3 = recv3[3:].decode()
print(base64.b64decode(recv3).decode())
# send req3: pwd

pwd = base64.b64encode(pwd.encode("utf-8")).decode()
print("C: ", pwd)
clientSocket.send((pwd + '\r\n').encode())
recv4 = clientSocket.recv(1024)
print("S: ", recv4[:3].decode(), end=" ")
recv4 = recv4[3:].decode()
print(recv4, end="")

#after login success,we begin to send mail
# sender
clientSocket.send(("MAIL FROM: " + mailfrom + '\r\n').encode())
recv = clientSocket.recv(1024)
print("S: ", recv[:3].decode(), end=" ")
recv = recv[3:].decode()
print(recv, end="")
# receiver

clientSocket.send(("RCPT TO: " + rcptto + '\r\n').encode())
recv = clientSocket.recv(1024)
print("S: ", recv[:3].decode(), end=" ")
recv = recv[3:].decode()
print(recv, end="")
# content req
clientSocket.send("DATA\r\n".encode())
recv = clientSocket.recv(1024)
print("S: ", recv[:3].decode(), end=" ")
recv = recv[3:].decode()
print(recv, end="")
# content detail
fromaddress = "ycy"
toaddress = "2458862813@qq.com"
subject = "test"
contenttype = "text/plain"
message = 'from:' + fromaddress + '\r\n'
message += 'to:' + toaddress + '\r\n'
message += 'subject:' + subject + '\r\n'
message += 'Content-Type:' + contenttype + '\t\n'
message += '\r\n' + msg

clientSocket.send(message.encode())
clientSocket.send("\r\n.\r\n".encode())
recv = clientSocket.recv(1024)
print("S: ", recv[:3].decode(), end=" ")
recv = recv[3:].decode()
print(recv, end="")
# close connect
# Send QUIT command and get server response.
clientSocket.send(endmsg.encode())
recv = clientSocket.recv(1024)
print("S: ", recv[:3].decode(), end=" ")
recv = recv[3:].decode()
print(recv, end="")
clientSocket.close()
