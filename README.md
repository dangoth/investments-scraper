# investments_scraper

My retirement fund is with a company which offers a web portal, however it is quite basic and offers very little in terms of insight into performance and historical values of the investments.

I wrote a simple scraper with selenium which grabs the total invested into obligations and shares, the count of ownership units, as well as total value of investments, and stores it in a sqlite database. 

Uses selenium and sqlite3. Username and password should be stored in `Credentials.java` as `username` and `password` strings.

Data format before parsing:

`BNP Paribas Globalny Dynamicznego Wzrostu kat. C Składka pracodawcy d ddd,dd PLN dd.dddddd
BNP Paribas Globalny Dynamicznego Wzrostu kat. C Składka pracownika dd ddd,dd PLN dd.dddddd
BNP Paribas Obligacji kat. C Składka pracodawcy d ddd,dd PLN dd.dddddd
BNP Paribas Obligacji kat. C Składka pracownika d ddd,dd PLN dd.dddddd`



# To be added:

* Set up a cron job to scrape the investments daily
* Once significant amount of data is gathered, create visualizations (pyplot, seaborn?)


# Version history

* v 0.1 - first java port
