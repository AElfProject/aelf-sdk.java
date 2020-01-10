package io.aelf.schemas;


public class TaskQueueInfoDto {
    private String Name;
    private int Size;
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        this.Name = name;
    }
    public int getSize() {
        return Size;
    }
    public void setSize(int size) {
        this.Size = size;
    }

}
