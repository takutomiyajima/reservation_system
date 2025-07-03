# reservation_system

## Build and Run

1. Start the bundled HSQLDB server:
   ```bash
   java -cp reservationsystem/dev_program_DB/lib/hsqldb.jar org.hsqldb.Server -database.0 file:reservationsystem/dev_program_DB/mydb/mydb -dbname.0 mydb
   ```
2. Compile the sources:
   ```bash
   javac -cp reservationsystem/Waseda-SE/lib/hsqldb.jar -d reservationsystem/Waseda-SE/bin reservationsystem/Waseda-SE/src/**/*.java
   ```
3. Initialize room data:
   ```bash
   java -cp "reservationsystem/Waseda-SE/bin:reservationsystem/dev_program_DB/lib/hsqldb.jar" app.setup.RoomSetup
   ```
4. Run the console UI:
   ```bash
   java -cp "reservationsystem/Waseda-SE/bin:reservationsystem/dev_program_DB/lib/hsqldb.jar" app.cui.CUI
   ```
