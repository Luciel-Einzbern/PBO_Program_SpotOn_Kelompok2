package SpotOn;

/**
 *  KELOMPOK 2
 *  - NANANG SAEPUDIN   - 2510631170011
 *  - FITRIA            - 2510631170023
 *  - DIMAS INDRAWiJAYA - 2510631170032
 */

public class Mobil extends Kendaraan {
	private double tarifPerJam;

	public Mobil(String platNomor, int lokasiParkir) {
		super(platNomor, JenisKendaraan.MOBIL, lokasiParkir);
		this.tarifPerJam = 5000.0;
	}

	public double getTarifPerJam() {
		return tarifPerJam;
	}
}