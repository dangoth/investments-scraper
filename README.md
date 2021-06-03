# investments_scraper

My retirement fund is with a company which offers a web portal, however it is quite basic and offers very little in terms of insight into performance and historical values of the investments.
I wrote a simple scraper with selenium which grabs the total invested into obligations and shares, the count of ownership units, as well as total value of investments, and stores it in a sqlite database. 
Requires selenium and sqlite3. Username and password should be stored in `credentials.py` as `user_login` and `user_password` strings.

# To be added:

* Possibly a bash script to setup a cron job to scrape the investments
* Duplicate entry per day prevention
* Cleaner data parsing
