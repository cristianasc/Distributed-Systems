import paramiko, sys, os

machines = [{
    "order": 1,
    "class": "AfternoonAtTheRaces.AfternoonAtTheRaces",
    "machine": "l040101-ws01.ua.pt"
  },
  {
    "order": 2,
    "class": "BettingCentre.BettingCentreServer",
    "machine": "l040101-ws02.ua.pt"
  },
  {
    "order": 3,
    "class": "ControlCentre.ControlCenterServer",
    "machine": "l040101-ws03.ua.pt"
  },
  {
    "order": 4,
    "class": "GeneralRepository.GeneralRepositoryServer",
    "machine": "l040101-ws04.ua.pt"
  },
  {
    "order": 5,
    "class": "Paddock.PaddockServer",
    "machine": "l040101-ws05.ua.pt"
  },
  {
    "order": 6,
    "class": "RacingTrack.RacingTrackServer",
    "machine": "l040101-ws06.ua.pt"
  },
  {
    "order": 7,
    "class": "Stable.StableServer",
    "machine": "l040101-ws7.ua.pt"
  },
  {
    "order": 8,
    "class": "Broker.Broker",
    "machine": "l040101-ws8.ua.pt"
  },
  {
    "order": 9,
    "class": "Horse.Horse",
    "machine": "l040101-ws9.ua.pt"
  },
  {
    "order": 10,
    "class": "Spectator.Spectator",
    "machine": "l040101-ws19.ua.pt"
}]

COMMAND = 'java -cp %s:libs/* %s'
USERNAME = "sd0107"
PASSWORD = "grupomaisfixedesempre96"

def sendFileToMachines():
    for s in machines:
        ssh.connect(s['machine'], username=USERNAME, password=PASSWORD)
        sftp = ssh.open_sftp()
        sftp.chdir("/home/sd0107/")
        sftp.put(os.getcwd() + "/"+FILENAME, FILENAME)
        sftp.put(os.getcwd() + "/"+'conf.xml', 'conf.xml')
        print ("Sending the .jar to the machine: " + s['machine'])
    ssh.close()

def executeServices():
    grService = None
    for s in machines:
        ssh.connect(s['machine'], username=USERNAME, password=PASSWORD)
        stdin, stdout, stderr = ssh.exec_command(COMMAND % ( FILENAME, s['class']))
        classname = s['class'].split('.')[-1]
        if classname == 'GeneralRepositoryRun':
          grService = stdout.channel
        print("Executing %s in %s" % (classname, s['machine']))

    print('Waiting for simulation to end...')
    if grService.recv_exit_status() == 0:
      print('Simulation has ended successfuly!!')
    ssh.close()

def killServices():
  pass

if __name__ == '__main__':
    FILENAME = sys.argv[1]
    ssh = paramiko.SSHClient()
    ssh.load_system_host_keys()
    ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())

    sendFileToMachines()
    executeServices()
    print('End Operations!!')
