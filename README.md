
# Queue Simulator
Queues are commonly used to model real world domains. The main objective of a queue is to
provide a place for a "client" to wait before receiving a "service". The management of queue-based
systems is interested in minimizing the time amount their "clients" are waiting in queues before
they are served. One way to minimize the waiting time is to add more servers, i.e. more queues in
the system (each queue is considered as having an associated processor) but this approach increases
the costs of the service supplier.
 
The application should simulate (by defining a simulation time Tš ššš¢ššš”ššš) a series of N clients
arriving for service, entering Q queues, waiting, being served and finally leaving the queues. All
clients are generated when the simulation is started, and are characterized by three parameters: ID
(a number between 1 and N), Tššššš£šš (simulation time when they are ready to go to the queue; i.e.
time when the client finished shopping) and Tš ššš£ššš (time interval or duration needed to serve the
client; i.e. waiting time when the client is in front of the queue). The application tracks the total
time spent by every client in the queues and computes the average waiting time. Each client is
added to the queue with minimum waiting time when its Tššššš£šš time is greater than or equal to
the simulation time (Tššššš£šš ā„ Tš ššš¢ššš”ššš). 




## Screenshots

![App Screenshot](https://i.imgur.com/Hj4uiZy.png)

![App Screenshot](https://i.imgur.com/2GuXWyo.png)
