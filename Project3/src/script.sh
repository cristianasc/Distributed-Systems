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
registryPortNum=22171

echo "compressing source code..."

tar -czf code.tar.gz registry/ monitors/ main/ interfaces/ states/

sleep 5

echo "Unziping code in machines"
sshpass -p $password scp code.tar.gz $username@$registryHostName:
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$registryHostName " tar -xmzf code.tar.gz" &

sshpass -p $password scp code.tar.gz $username@$GeneralRepositoryHostName:
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$GeneralRepositoryHostName " tar -xmzf code.tar.gz;" &

sshpass -p $password scp code.tar.gz $username@$PaddockHostName:
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$PaddockHostName " tar -xmzf code.tar.gz; " &

sshpass -p $password scp code.tar.gz $username@$StableHostName:
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$StableHostName " tar -xmzf code.tar.gz; " &

sshpass -p $password scp code.tar.gz $username@$BettingCenterHostName:
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$BettingCenterHostName " tar -xmzf code.tar.gz; " &

sshpass -p $password scp code.tar.gz $username@$RaceTrackHostName:
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$RaceTrackHostName " tar -xmzf code.tar.gz; " &

sshpass -p $password scp code.tar.gz $username@$ControlCenterHostName:
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$ControlCenterHostName " tar -xmzf code.tar.gz; " &

sshpass -p $password scp code.tar.gz $username@$BrokerHostName:
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$BrokerHostName " tar -xmzf code.tar.gz; " &

sshpass -p $password scp code.tar.gz $username@$HorsesHostName:
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$HorsesHostName " tar -xmzf code.tar.gz; " &

sshpass -p $password scp code.tar.gz $username@$SpectatorHostName:
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$SpectatorHostName " tar -xmzf code.tar.gz; " &




echo "Setting RMI repository.... "
echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$registryHostName " javac interfaces/*.java" &
sleep 2

echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$registryHostName " javac registry/*.java" &
sleep 2

echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$registryHostName " rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false 22171;" &
sleep 2

sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$registryHostName " java registry/ServerRegisterRemoteObject l040101-ws01.ua.pt 22171" &
sleep 5
echo " "


echo "Setting General Repository.... "
echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$GeneralRepositoryHostName " javac interfaces/*.java" &
sleep 2

echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$GeneralRepositoryHostName " javac monitors/GeneralRepository/*.java" &
sleep 2

echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$GeneralRepositoryHostName " java monitors/GeneralRepository/GeneralRepositoryStart 22177 l040101-ws01.ua.pt 22171" &
sleep 5
echo " "


echo "Setting Control Center.... "
echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$ControlCenterHostName " javac interfaces/*.java" &
sleep 2

echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$ControlCenterHostName " javac monitors/ControlCentre/*.java" &
sleep 2

echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$ControlCenterHostName " java monitors/ControlCentre/ControlCentreStart 22174 l040101-ws01.ua.pt 22171" &
sleep 5
echo " "


echo "Setting Betting Center.... "
echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$BettingCenterHostName " javac interfaces/*.java" &
sleep 2

echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$BettingCenterHostName " javac monitors/BettingCentre/*.java" &
sleep 2

echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$BettingCenterHostName " java monitors/BettingCentre/BettingCentreStart 22175 l040101-ws01.ua.pt 22171" &
sleep 5
echo " "


echo "Setting RaceTrack.... "
echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$RaceTrackHostName " javac interfaces/*.java" &
sleep 2

echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$RaceTrackHostName " javac monitors/RacingTrack/*.java" &
sleep 2

echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$RaceTrackHostName " java monitors/RacingTrack/RacingTrackStart 22176 l040101-ws01.ua.pt 22171" &
sleep 5
echo " "


echo "Setting Stable.... "
echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$StableHostName " javac interfaces/*.java" &
sleep 2

echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$StableHostName " javac monitors/Stable/*.java" &
sleep 2

echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$StableHostName "java monitors/Stable/StableStart 22177 l040101-ws01.ua.pt 22171" &
sleep 5
echo " "


echo "Setting Paddock.... "
echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$PaddockHostName " javac interfaces/*.java" &
sleep 2

echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$PaddockHostName " javac monitors/Paddock/*.java" &
sleep 2

echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$PaddockHostName " java monitors/Paddock/PaddockStart 22178 l040101-ws01.ua.pt 22171" &
sleep 5
echo " "


echo "Setting Broker.... "
echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$BrokerHostName " javac interfaces/*.java" &
sleep 2

echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$BrokerHostName " javac main/*.java" &
sleep 2

echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$BrokerHostName " java main/BrokerStart l040101-ws01.ua.pt 22171" &
sleep 5
echo " "

echo "Setting Horses.... "
echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$HorsesHostName " javac interfaces/*.java" &
sleep 2

echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$HorsesHostName " javac main/*.java" &
sleep 2

echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$HorsesHostName " java main/HorseStart l040101-ws01.ua.pt 22171" &
sleep 5
echo " "

echo "Setting Spectator.... "
echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$SpectatorHostName " javac interfaces/*.java" &
sleep 2

echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$SpectatorHostName " javac main/*.java" &
sleep 2

echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$SpectatorHostName " java main/SpectatorStart l040101-ws01.ua.pt 22171" &
sleep 5
echo " "

sh clean_class_zip.sh
echo " "
