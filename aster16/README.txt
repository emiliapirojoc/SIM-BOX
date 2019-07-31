1. Build docker file #docker build . -t asterisk13
2. run container with #docker run --name aster3 -p 10000-10009 -p 10000-10009/udp -p 8088:8088 -p 8089:8089 -p 5060:5060 -p 5060:5060/udp -P -d asterisk13
=================================================
Name                            Port    Group
sip                             5060	VOIP
sip-t	                        5061	VOIP
Asterisk API	                5038	Asterisk
Media Gateway Control Protocol	2727	MGCP
=================================================

edit /etc/asterisk/rtp.conf port range to be between 10000-10009
Add Nat support
in /etc/asterisk/sip.conf find NAT SUPPORT section
and add
localnet=192.168.0.0/255.255.0.0
externaddr = 192.168.88.35
nat=yes




