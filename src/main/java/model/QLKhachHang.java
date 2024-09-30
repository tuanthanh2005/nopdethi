package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import util.FileHelper;

public class QLKhachHang {
    private ArrayList<KhachHang> dsKhachHang;
    
    
    public ArrayList<KhachHang> getdskhachhang(){
        return dsKhachHang;
    }

    public QLKhachHang() {
        dsKhachHang = new ArrayList<>();
    
    }

    // Đọc danh sách khách hàng từ file input.txt
    public void docKhachHang(String filename) {
        ArrayList<String> data = FileHelper.readFileText(filename);
        for (String line : data) {
            String[] parts = line.split(";");
            String maso = parts[0];
            String hoten = parts[1];
            int sonhankhau = Integer.parseInt(parts[2]);
            double chisocu = Double.parseDouble(parts[3]);
            double chisomoi = Double.parseDouble(parts[4]);
            dsKhachHang.add(new KhachHang(maso, hoten, sonhankhau, chisocu, chisomoi));
        }
    }

    // Tìm số khối nước tiêu thụ cao nhất
    public double getTieuThuCaoNhat() {
        return dsKhachHang.stream().mapToDouble(KhachHang::getTieuThu).max().orElse(0);
    }

    // Tìm số khối nước tiêu thụ trung bình
    public double getTieuThuTrungBinh() {
        return dsKhachHang.stream().mapToDouble(KhachHang::getTieuThu).average().orElse(0);
    }

   
    public void sapXepTheoTieuThuGiamDan() {
        Collections.sort(dsKhachHang, Comparator.comparingDouble(KhachHang::getTieuThu).reversed());
    }

   
    public boolean ghiHoaDon(String filename) {
        ArrayList<String> data = new ArrayList<>();
        for (KhachHang kh : dsKhachHang) {
            String line = kh.getMaso() + ";" + kh.getHoten() + ";" + kh.getTieuThu() + ";" + kh.tinhTienTra()+";";
            data.add(line);
        }
        return FileHelper.writeFileText(filename, data);
    }
}
