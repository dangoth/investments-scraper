# investments_scraper

**Java version available as a separate branch**

My retirement fund is with a company which offers a web portal, however it is quite basic and offers very little in terms of insight into performance and historical values of the investments.

I wrote a simple scraper with selenium which grabs the total invested into obligations and shares, the count of ownership units, as well as total value of investments, and stores it in a sqlite database. 

Requires selenium and sqlite3. Username and password should be stored in `credentials.py` as `user_login` and `user_password` strings.

# To be added:

* Set up a cron job to scrape the investments daily
* Duplicate entry per day prevention
* Cleaner data parsing


# Version history

* v 0.2 - added check if db exists, date format without time, protection against duplicating query for the day
* v 0.1 - first commit
