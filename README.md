# reservation_system

## Build and Run

1. Start the bundled HSQLDB server:
   ```bash
   java -cp reservationsystem/dev_program_DB/lib/hsqldb.jar org.hsqldb.Server -database.0 file:reservationsystem/dev_program_DB/mydb/mydb -dbname.0 mydb
   ```

2. **First time only:** either run `setup.sql` using the HSQLDB tools or delete the existing `mydb.properties` and `mydb.script` files before starting the server so the schema is created again. The `setup.sql` script creates all tables including the `ROOM` table with its `TYPE` column required by `RoomSqlDao.createRoom`.

3. Compile the sources:
   ```bash
   javac -cp reservationsystem/Waseda-SE/lib/hsqldb.jar -d reservationsystem/Waseda-SE/bin reservationsystem/Waseda-SE/src/**/*.java
   ```
4. Initialize room data:
   ```bash
   java -cp "reservationsystem/Waseda-SE/bin:reservationsystem/dev_program_DB/lib/hsqldb.jar" app.setup.RoomSetup
   ```
5. Run the console UI:
   ```bash
   java -cp "reservationsystem/Waseda-SE/bin:reservationsystem/dev_program_DB/lib/hsqldb.jar" app.cui.CUI
   ```
