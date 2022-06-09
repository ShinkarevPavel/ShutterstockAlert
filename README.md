					ShutterStockAlert 

Application developed by me and can be used by any person that have contributor account on Shutterstock. 
The application is intended to be an alert downloads. One of the main goal is send notifications to telegram with flexible schedule.
Schedule can be changed in runtime. Schedule notifications might be changed in application runtime(using public API, or by command in Telegram bot). 
Also in telegram bot there is possible to get month downloads and earnings. 

One of the important things is telegram bot should be created by you(using telegram API). And credentials should be set by this application public API. The main reason of this solution is privacy your shutterstock account.

For using public API of application, for all POST request you should have secret key for making changes. You can change it previously encrypt in KeyValidator class .

Application uses not public Shutterstock API, thats why there is need provide to application all headers for sending for each request to shutterstock backend. You can find headers in your browser using developer tools.