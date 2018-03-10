#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <string.h>

#define true 1
#define MAXBUFLEN 4096
#define SERVER_PORT 3333

struct sockaddr_in6 udpFdAddress;
int numbytes;
struct sockaddr_in6 clientAddr;
socklen_t addrLen = sizeof(clientAddr);
char buf[MAXBUFLEN];
char s[INET6_ADDRSTRLEN];

int initUdpSocket()
{
         socket(AF_INET6, SOCK_DGRAM, 0);
}

int listenPackages(int udpFd, int argc, char** argv)
{

	// If the argument is one bind to all IP addresses of the server
	if(argc == 1)
	{
		udpFdAddress.sin6_family = AF_INET6;
		udpFdAddress.sin6_addr = in6addr_any;
		udpFdAddress.sin6_port = htons(SERVER_PORT);
	}
	else 
	{
		if (inet_pton(AF_INET6, argv[1], &(udpFdAddress.sin6_addr)) != 1) 
		{
			fprintf(stderr, "Invalid address\n");
			return 1;
		}
		udpFdAddress.sin6_family = AF_INET6;
		udpFdAddress.sin6_port = htons(SERVER_PORT);
		
		char ipinput[INET6_ADDRSTRLEN];
		inet_ntop(AF_INET6, &(udpFdAddress.sin6_addr), ipinput, INET6_ADDRSTRLEN);
		printf("IP Address = %s\n", ipinput);

	}
	if(bind(udpFd, (struct sockaddr *)&udpFdAddress, sizeof udpFdAddress) == -1)
	{
		perror("bind");
		return 1;
	}
	while(true)
	{
		if ((numbytes = recvfrom(udpFd, buf, MAXBUFLEN - 1, 0, (struct sockaddr *)&clientAddr, &addrLen)) == -1) 
		{
			perror("recvfrom");
			return 1;
		}

		printf("server: got packet from %s\n",
		inet_ntop(clientAddr.sin6_family,
		&clientAddr.sin6_addr, s, sizeof s));
		printf("server: packet is %d bytes long\n", numbytes);
		buf[numbytes] = '\0';
		printf("server: packet contains \"%s\"\n", buf);
	}
	close(udpFd);
	return 0;
}


int main(int argc, char *argv[])
{
	int udpFd = initUdpSocket();

	if(udpFd == -1)
	{
		perror("Could not create socket");
		return 1;
	}
	
	memset(&udpFdAddress, 0, sizeof(udpFdAddress));

	return listenPackages(udpFd, argc, argv);
}
