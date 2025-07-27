package model.bean;

/**
 * Bean class for NhanVien (Employee)
 */
public class NhanVien {
    private String idNV;
    private String hoTen;
    private String idPB;
    private String diaChi;
    
    // Default constructor
    public NhanVien() {}
    
    // Constructor with parameters
    public NhanVien(String idNV, String hoTen, String idPB, String diaChi) {
        this.idNV = idNV;
        this.hoTen = hoTen;
        this.idPB = idPB;
        this.diaChi = diaChi;
    }
    
    // Constructor without ID (for insert operations)
    public NhanVien(String hoTen, String idPB, String diaChi) {
        this.hoTen = hoTen;
        this.idPB = idPB;
        this.diaChi = diaChi;
    }
    
    // Getters and Setters
    public String getIdNV() {
        return idNV;
    }
    
    public void setIdNV(String idNV) {
        this.idNV = idNV;
    }
    
    public String getHoTen() {
        return hoTen;
    }
    
    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }
    
    public String getIdPB() {
        return idPB;
    }
    
    public void setIdPB(String idPB) {
        this.idPB = idPB;
    }
    
    public String getDiaChi() {
        return diaChi;
    }
    
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
    
    @Override
    public String toString() {
        return "NhanVien{" +
                "idNV='" + idNV + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", idPB='" + idPB + '\'' +
                ", diaChi='" + diaChi + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        NhanVien nhanVien = (NhanVien) obj;
        return idNV != null ? idNV.equals(nhanVien.idNV) : nhanVien.idNV == null;
    }
    
    @Override
    public int hashCode() {
        return idNV != null ? idNV.hashCode() : 0;
    }
}