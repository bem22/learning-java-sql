### Christmas Crackers
### Cracker  

|INTEGER|VARCHAR|INT|INT|INT|INT|INT|
|-----------------------------------|
|cid|name|jid|hid|gid|quantity|saleprice|

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
    CONSTRAINT Joke_Foreign FOREIGN KEY (jid) REFERENCES Jokes(jid) ON DELETE CASCADE
```
* _HID_ foreign key
```sql
    CONSTRAINT Hat_Foreign FOREIGN KEY (hid) REFERENCES Hats(hid) ON DELETE CASCADE
```
* _GID_ foreign key
```sql
    CONSTRAINT Gift_Foreign FOREIGN KEY (gid) REFERENCES Gifts(gid) ON DELETE CASCADE
```

##### CODE
```sql
CREATE TABLE Crackers(
  cid INTEGER,
  name VARCHAR NOT NULL,
  jid INTEGER NOT NULL,
  hid INTEGER NOT NULL,
  gid INTEGER NOT NULL,
  quantity INTEGER NOT NULL,
  saleprice INTEGER NOT NULL,
  CONSTRAINT Cracker_Primary PRIMARY KEY (cid),
  CONSTRAINT Cracker_Name_Unique UNIQUE(name),
  CONSTRAINT Joke_Foreign FOREIGN KEY (jid) REFERENCES Jokes(jid) ON DELETE CASCADE,
  CONSTRAINT Hat_Foreign FOREIGN KEY (hid) REFERENCES Hats(hid) ON DELETE CASCADE,
  CONSTRAINT Gift_Foreign FOREIGN KEY (gid) REFERENCES Gifts(gid) ON DELETE CASCADE
)
```
#### Jokes

|INTEGER|VARCHAR|INT|
|-------------------|
|jid|joke|royalty|

Constraints:
* _JID_ primary key
```sql
    CONSTRAINT Joke_Primary PRIMARY KEY(jid)

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
  royalty INTEGER NOT NULL,
  CONSTRAINT Joke_Primary PRIMARY KEY(jid),
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
    CONSTRAINT Hat_Primary PRIMARY KEY(hid)
```
##### Code
```sql
CREATE TABLE Hats(
  hid INTEGER,
  description VARCHAR NOT NULL,
  price INTEGER NOT NULL,
  CONSTRAINT Hat_Primary PRIMARY KEY(hid)
)

```
#### Gifts

|INTEGER|VARCHAR|INT|
|-------------------|
|gid|description|price|

Constraints:
* _GID_ primary key
```sql
    CONSTRAINT Gift_Primary PRIMARY KEY (gid)
```
##### Code
```sql
CREATE TABLE Gifts(
  gid INTEGER,
  description VARCHAR NOT NULL,
  price INTEGER NOT NULL,
  CONSTRAINT Gi_Primary PRIMARY KEY(gid)
)

```
