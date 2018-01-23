# MahoutWebAPI:
Mahout WebAPI for PrestaShop (tested on 1.7 version)

## Overview:
Final view of this system:

![alt text](https://raw.githubusercontent.com/michal13wat/MahoutWebAPI/master/resources/Screenshot.png)

In the picture you can see, that this system needs additional "modules" (marked red).
It is made by injection of JavaScript code by Google Tag Manager.
(For use GTM you need install add-on module for your PrestaShop).
Recommended products are shown on on each product page.


## How does it works:
This system recommendations use Mahout library (item based similarity).
Loged in users rate bought products. This inforation is send to MahoutWebAPI and later
it is saved in database of your PrestaShop. This informations are analysed by 
Mahout Core. When some user rate several products and Mahout detect, that this
user buy items similar to group other customers and similary rate this products, then this system will display 
recommendations for this user. 


## Preriquests:
1. Install Docker Engine (tested on version 17.12.0-ce, build c97c6d6)

2. Install Docker-Compose (tested on docker-compose version 1.8.0)


## How to run it:
This system is build on 4 Docker containers.
If you want to quickly see how does example work the esiest way is run
it by docker-compose. 
To this end clone this repository, in terminal enter to project home directory
(directory where you can see such subdirectories as resources, src).
Type:
```bash
docker-compose up
```
This command caused dowload 4 docker containers from DockerHub:
- MahoutWebAPI    (app, which is placed in this repository)
- PrestaShop 1.7
- MySQL
- PhpMyAdmin

Now you should be patient. It can take several minutes.
After dowload it will consume about 3.5GB of your disk space.
After do this you should go to:
```uri
localhost:81
```
(PhpMyAdmin).
Go to prestashop database and import example_inserts.sql.
Next for this DB create user: mahoutapi and password: admin 
with privileges at least reading and writeing to DB.
Obviviously you can make any user and password, but for test I suggest try with this.
Later go to:
```uri
localhost/rootjkow
```
(administration panel of PrestaShop).
login as admin (look at the Another passwords paragraph)
and go to:
KONFIGURUJ -> Preferencje -> Ruch -> USTAW URL SKLEPU 
and click "Zapisz". 
(It will set store domain.)
Even if you see proper domain name, you have to do this,
because it is probably bug of PrestaShop.

Next logout from admin panel, go to:
```uri
localhost
```
and login as one example user. Then if you enter page any
product you should see recommendations.

## Example users:
If you uploaded to database of PrestaShop example_inserts.sql file,
then you will have acces to 5 example users accounts. For this users
you will seen recommendations, because they rate several products.

e-mail: kaa@migmail.pl
passwd: kaa@migmail.pl

e-mail: kbb@migmail.pl
passwd: kbb@migmail.pl

e-mail: kcc@migmail.pl
passwd: kcc@migmail.pl

e-mail: kdd@migmail.pl
passwd: kdd@migmail.pl

e-mail: kee@migmail.pl
passwd: kee@migmail.pl


## Another passowrds:
- PhpMyAdmin:
  - user:   root
  - passwd: admin
- PrestaShop:
  - user:   sklepcegielka@gmail.com
  - passwd: admin
  
  
## Maven in docker container:
How to make changes in project?
In Docker image of container named:
```txt
brzozaxd/mahoutapi
```
is installed Maven which, is used for
building content of this container.
If you made changes in project you should enter in terminal to project home directory
and there type command:
```bash
docker build -t brzozaxd/mahoutapi .
```
This command rebuild image.

## Tips:
  - for tests do not use private mode / incognito in your browser 
  - for tests turn off all add-ons in your browser blockings ads, because
    it can cause blocking injection JavaScript code by GTM



