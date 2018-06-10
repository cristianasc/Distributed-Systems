#!/usr/bin/env bash
username=sd0107
password=grupomaisfixedesempre96
url=http://192.168.8.171/sd0107/classes/
registryHostName=l040101-ws01.ua.pt
GeneralRepositoryHostName=l040101-ws02.ua.pt
ControlCenterHostName=l040101-ws03.ua.pt
BettingCenterHostName=l040101-ws04.ua.pt
RaceTrackHostName=l040101-ws05.ua.pt
StableHostName=l040101-ws06.ua.pt
PaddockHostName=l040101-ws06.ua.pt
BrokerHostName=l040101-ws08.ua.pt
HorsesHostName=l040101-ws09.ua.pt
SpectatorHostName=l040101-ws10.ua.pt
registryPortNum=22170

find . -maxdepth 8 -type f -name "*.class" -delete

rm -rf *.tar.gz
echo "Cleaning..."
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$registryHostName "rm -rf *"
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$GeneralRepositoryHostName "rm -rf *"
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$ControlCenterHostName " rm -rf *"
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$BettingCenterHostName " rm -rf *"
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$RaceTrackHostName " rm -rf *"
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$StableHostName " rm -rf *"
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$PaddockHostName " rm -rf *"
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$BrokerHostName " rm -rf *"
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$HorsesHostName " rm -rf *"
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$SpectatorHostName " rm -rf *"
echo "Cleaning done! all .class & folders deleted!"