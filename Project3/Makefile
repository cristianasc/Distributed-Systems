JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java -d __bin

all:	__bin classes

clean:
	rm -r __bin/

CLASSES = \
        src/Interfaces/*.java \
        src/registry/RegisterRemoteObject.java \
        src/registry/ServerRegisterRemoteObject.java \
        src/monitors/BettingCentre/*.java \
        src/interfaces/*.java \
        src/main/*.java \
        src/states/*.java \

classes: $(CLASSES:.java=.class)

__bin:  
	mkdir __bin