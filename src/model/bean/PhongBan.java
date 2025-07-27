package model.bean;

/**
 * Bean class for PhongBan (Department)
 */
public class PhongBan {
    private String idPB;
    private String tenPB;
    private String moTa;
    
    // Default constructor
    public PhongBan() {}
    
    // Constructor with parameters
    public PhongBan(String idPB, String tenPB, String moTa) {
        this.idPB = idPB;
        this.tenPB = tenPB;
        this.moTa = moTa;
    }
    
    // Constructor without ID (for insert operations)
    public PhongBan(String tenPB, String moTa) {
        this.tenPB = tenPB;
        this.moTa = moTa;
    }
    
    // Getters and Setters
    public String getIdPB() {
        return idPB;
    }
    
    public void setIdPB(String idPB) {
        this.idPB = idPB;
    }
    
    public String getTenPB() {
        return tenPB;
    }
    
    public void setTenPB(String tenPB) {
        this.tenPB = tenPB;
    }
    
    public String getMoTa() {
        return moTa;
    }
    
    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
    
    @Override
    public String toString() {
        return "PhongBan{" +
                "idPB='" + idPB + '\'' +
                ", tenPB='" + tenPB + '\'' +
                ", moTa='" + moTa + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        PhongBan phongBan = (PhongBan) obj;
        return idPB != null ? idPB.equals(phongBan.idPB) : phongBan.idPB == null;
    }
    
    @Override
    public int hashCode() {
        return idPB != null ? idPB.hashCode() : 0;
    }
}