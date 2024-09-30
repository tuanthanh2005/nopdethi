package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.QLKhachHang;
import model.KhachHang;


public class FrmQLKhachHang extends JFrame {
    private JTable tblKhachHang;
    private JButton btDocFile, btGhiFile;
    private JCheckBox chkSapXep;
    private JTextField txtMax, txtTB;
    private QLKhachHang qlkh;

    public FrmQLKhachHang(String title) {
        super(title);
        qlkh = new QLKhachHang();
        createGUI();
        addEvents();
          pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void createGUI() {
        setLayout(new BorderLayout());

        // Table
        String[] columnNames = {"Mã số", "Họ tên","Số Nhân Khẩu","Chỉ Số Cũ","Chỉ Số Mới", "Số tiêu thụ", "Vượt định mức", "Tiền trả"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        tblKhachHang = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tblKhachHang);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel panel = new JPanel();
        btDocFile = new JButton("Nhập dữ liệu khách hàng");
        btGhiFile = new JButton("Xuất hóa đơn thanh toán");
        chkSapXep = new JCheckBox("Sắp xếp");
        panel.add(btDocFile);
        panel.add(btGhiFile);
        panel.add(chkSapXep);
        add(panel, BorderLayout.NORTH);

        // Text fields
        JPanel panel2 = new JPanel();
        txtMax = new JTextField(10);
        txtTB = new JTextField(10);
        panel2.add(new JLabel("Cao nhất:"));
        panel2.add(txtMax);
        panel2.add(new JLabel("Trung bình:"));
        panel2.add(txtTB);
        add(panel2, BorderLayout.SOUTH);
    }

    private void addEvents() {
        btDocFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                qlkh.docKhachHang("input.txt");
                updateTable();
                txtMax.setText(String.valueOf(qlkh.getTieuThuCaoNhat()));
                txtTB.setText(String.valueOf(qlkh.getTieuThuTrungBinh()));
            }
        });

        chkSapXep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chkSapXep.isSelected()) {
                    qlkh.sapXepTheoTieuThuGiamDan();
                    updateTable();
                }
            }
        });

        btGhiFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (qlkh.ghiHoaDon("output.txt")) {
                    JOptionPane.showMessageDialog(null, "Xuất hóa đơn thành công!");
                } else {
                    JOptionPane.showMessageDialog(null, "Xuất hóa đơn thất bại!");
                }
            }
        });
    }

    private void updateTable() {
        DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();
        model.setRowCount(0);
       for (KhachHang kh : qlkh.getdskhachhang())
       {
           String vuotDinhMuc = kh.getTieuThu() > kh.getDinhMuc() ? "X" : "";
           Object[] row = {kh.getMaso(), kh.getHoten(), kh.getTieuThu(), vuotDinhMuc, kh.tinhTienTra()};
           model.addRow(row);
       }
    }
}
