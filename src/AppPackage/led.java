

package AppPackage;

import javax.swing.JOptionPane;

public class led {
    int id;
    char port;
    int bitNo;
    int x_axis=50;
    int y_axis=50;

    public led() {
        id=0;
        bitNo=1;
        x_axis=y_axis=50;
        port='B';
        
    }
    
    public led(char port, int bitNo) {
        this.port = port;
        this.bitNo = bitNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getPort() {
        return port;
    }

    public int getBitNo() {
        return bitNo;
    }

    public void setPort(char port) {
        this.port = port;
    }

    public void setBitNo(int bitNo) {
        this.bitNo = bitNo;
    }

    public int getX_axis() {
        return x_axis;
    }

    public int getY_axis() {
        return y_axis;
    }

    public void setX_axis(int x_axis) {
        this.x_axis = x_axis;
    }

    public void setY_axis(int y_axis) {
        this.y_axis = y_axis;
    } 
    public void returnCode(){
     //   JOptionPane.showMessageDialog(null,"TRIS"+port+".B"+bitNo);
        
    }
     String Setting()
    {
    return "void LCDSetting(){\n"
            + "TRIS"+this.port+"."+this.port+this.bitNo+"=0;\n"
            + "PORT"+this.port+"."+this.port+this.bitNo+"=0;\n"
            + "}";
    }
}
