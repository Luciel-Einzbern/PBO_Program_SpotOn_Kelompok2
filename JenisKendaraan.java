package SpotOn;

/**
 *  KELOMPOK 2
 *  - NANANG SAEPUDIN   - 2510631170011
 *  - FITRIA            - 2510631170023
 *  - DIMAS INDRAWiJAYA - 2510631170032
 */

public enum JenisKendaraan {
	MOBIL("Mobil"), MOTOR("Motor");

	private String namaJenis;

	JenisKendaraan(String namaJenis) {
		this.namaJenis = namaJenis;
	}

	public String getNamaJenis() {
		return namaJenis;
	}
}