### Christmas Crackers
### Cracker  

|INTEGER|VARCHAR|INT|INT|INT|INT|INT|
|-----------------------------------|
|cid|name|jid|gid|hid|saleprice|quantity|

Constraints:
* _CID_ primary key
```sql
    CONSTRAINT Cracker_Primary PRIMARY KEY (cid)
```
* _name_ unique
```sql
    CONSTRAINT Cracker_Name_Unique UNIQUE (name)
```
* _JID_ foreign key
```sql
    CONSTRAINT Joke_Foreign FOREIGN KEY (jid) REFERENCES Jokes(jid)
```
* _HID_ foreign key
```sql
    CONSTRAINT Hat_Foreign FOREIGN KEY (hid) REFERENCES Hats(hid)
```
* _GID_ foreign key
```sql
    CONSTRAINT Gift_Foreign FOREIGN KEY (gid) REFERENCES Gifts(gid)
```

##### CODE
```sql
CREATE TABLE Crackers(
  cid INTEGER,
  name VARCHAR NOT NULL,
  jid INTEGER,
  hid INTEGER,
  gid INTEGER,
  CONSTRAINT Cracker_Primary PRIMARY KEY (cid),
  CONSTRAINT Cracker_Name_Unique UNIQUE(name),
  CONSTRAINT Joke_Foreign FOREIGN KEY (jid) REFERENCES Jokes(jid),
  CONSTRAINT Hat_Foreign FOREIGN KEY (hid) REFERENCES Hats(hid),
  CONSTRAINT Gift_Foreign FOREIGN KEY (gid) REFERENCES Gifts(gid)
)
```
#### Jokes

|INTEGER|VARCHAR|INT|
|-------------------|
|jid|joke|royality|

Constraints:
* _JID_ primary key
```sql
    CONSTRAINT Joke_Primary PRIMARY KEY (jid)

```
* _joke_ unique
```sql
    CONSTRAINT Joke_Joke_Unique UNIQUE(joke)
```

##### code
```sql
CREATE TABLE Jokes(
  jid INTEGER,
  joke VARCHAR NOT NULL,
  royality INTEGER NOT NULL,
  CONSTRAINT Joke_Primary PRIMARY KEY (jid),
  CONSTRAINT Joke_Joke_Unique UNIQUE (joke)
)
```
#### Hats

|INTEGER|VARCHAR|INT|
|-------------------|
|hid|description|price|

Constraints:
* _HID_ primary key
```sql
    CONSTRAINT Hat_Primary PRIMARY KEY (hid)
```
##### Code
```sql
CREATE TABLE Hats(
  hid INTEGER,
  description VARCHAR NOT NULL,
  price INTEGER NOT NULL,
  CONSTRAINT Hat_Primary PRIMARY KEY (hid)
)

```
#### Gifts

|INTEGER|VARCHAR|INT|
|-------------------|
|gid|description|price|

Constraints:
* _GID_ primary key
```sqlft
    CONSTRAINT Gift_Primary PRIMARY KEY (gid)
```
##### Code
```sql
CREATE TABLE Gifts(
  gid INTEGER,
  description VARCHAR NOT NULL,
  price INTEGER NOT NULL,
  CONSTRAINT Gi_Primary PRIMARY KEY (gid)
)

```
