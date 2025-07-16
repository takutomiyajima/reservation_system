# Reservation System

本リポジトリは、Java と HSQLDB を用いたコンソールベースの予約システムです。

## ビルドと実行手順

以下の手順でプロジェクトを構築・実行できます。

---

### 1. HSQLDB サーバの起動

まず、バンドルされている HSQLDB サーバを以下のコマンドで起動します：

```bash
java -cp reservationsystem/dev_program_DB/lib/hsqldb.jar org.hsqldb.Server -database.0 file:reservationsystem/dev_program_DB/mydb/mydb -dbname.0 mydb
```

#### 初回起動時の注意
初回起動時は以下のいずれかを行ってください：

- `setup.sql` を HSQLDB のツールで実行する
- または `mydb.properties` および `mydb.script` を削除してサーバを再起動し、スキーマを再作成

> `setup.sql` は、`ROOM` テーブルと `TYPE` カラムを含む、必要なテーブルをすべて作成します。

---

### 2. Java ソースコードのコンパイル

以下のコマンドで Java ソースをコンパイルします：

```bash
javac -cp reservationsystem/Waseda-SE/lib/hsqldb.jar -d reservationsystem/Waseda-SE/bin reservationsystem/Waseda-SE/src/**/*.java
```

---

### 3. 初期データの投入（ROOM テーブル）

以下のコマンドで `ROOM` テーブルに初期データを投入します：

```bash
java -cp "reservationsystem/Waseda-SE/bin:reservationsystem/dev_program_DB/lib/hsqldb.jar" app.setup.RoomSetup
```

---

### 4. コンソール UI（CUI）の実行

以下のコマンドで CUI を起動します：

```bash
java -cp "reservationsystem/Waseda-SE/bin:reservationsystem/dev_program_DB/lib/hsqldb.jar" app.cui.CUI
```

---
