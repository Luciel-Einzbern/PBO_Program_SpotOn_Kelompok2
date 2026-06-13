package SpotOn;

/**
 *  KELOMPOK 2
 *  - NANANG SAEPUDIN   - 2510631170011
 *  - FITRIA            - 2510631170023
 *  - DIMAS INDRAWiJAYA - 2510631170032
 */

import java.time.LocalDateTime;

public class Kendaraan {
	private String platNomor;
	private JenisKendaraan jenis;
	private LocalDateTime waktuMasuk;
	private int lokasiParkir;

	public Kendaraan(String platNomor, JenisKendaraan jenis, int lokasiParkir) {
		this.platNomor = platNomor;
		this.jenis = jenis;
		this.lokasiParkir = lokasiParkir;
		this.waktuMasuk = DatabaseParkir.getWaktuSistem();
	}

	public String getPlatNomor() {
		return platNomor;
	}

	public JenisKendaraan getJenis() {
		return jenis;
	}

	public LocalDateTime getWaktuMasuk() {
		return waktuMasuk;
	}

	public int getLokasiParkir() {
		return lokasiParkir;
	}
}