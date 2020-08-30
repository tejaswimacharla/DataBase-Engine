import java.io.RandomAccessFile;
import java.util.ArrayList;
public class Index {
    public static void createIndex(String tableName, String colName, String dType) {

        int serialCode = 0;
        int record_size = 0;
        if (dType.equalsIgnoreCase("int")) {
            record_size = record_size + 4;
            serialCode = 0x06;
        } else if (dType.equalsIgnoreCase("tinyint")) {
            record_size = record_size + 1;
            serialCode = 0x04;
        } else if (dType.equalsIgnoreCase("smallint")) {
            record_size = record_size + 2;
            serialCode = 0x05;
        } else if (dType.equalsIgnoreCase("bigint")) {
            record_size = record_size + 8;
            serialCode = 0x07;
        } else if (dType.equalsIgnoreCase("real")) {
            record_size = record_size + 4;
            serialCode = 0x08;
        } else if (dType.equalsIgnoreCase("double")) {
            record_size = record_size + 8;
            serialCode = 0x09;
        } else if (dType.equalsIgnoreCase("datetime")) {
            record_size = record_size + 8;
            serialCode = 0x0A;
        } else if (dType.equalsIgnoreCase("date")) {
            record_size = record_size + 8;
            serialCode = 0x0B;
        } else if (dType.equalsIgnoreCase("text")) {
            //record_size=record_size+8;
            serialCode = 0x0C;
        }
        int ordinal_pos1 = 0;
        ArrayList < String > ord_position = new ArrayList < > ();

        for (int o = 0; o < 16; o++) {
            ord_position.add(".");
        }

        try {
            RandomAccessFile colFile1 = new RandomAccessFile("data/" + colName + ".ndx", "rw");
            colFile1.setLength(512);
            colFile1.seek(0);
            colFile1.writeByte(serialCode);
            int null_val = 1;
            colFile1.writeByte(null_val);
            colFile1.write(ordinal_pos1);
            ordinal_pos1++;
            colFile1.writeByte(0x00);
            colFile1.writeByte(0x10);
            colFile1.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}