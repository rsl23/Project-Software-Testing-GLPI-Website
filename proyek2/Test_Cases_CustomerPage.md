# Test Cases - Customer Page

## Informasi Proyek

- **Website**: GLPI Project - Customer Page
- **URL**: https://www.glpi-project.org/customers/
- **Tester**: [Nama Tester]
- **Tanggal Eksekusi**: 7 Januari 2026

---

## Test Case 1: Verify Success Stories Headline

| **Field**                 | **Detail**                                                                                                                                                  |
| ------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Test Case ID**          | TC-CUST-001                                                                                                                                                 |
| **Fitur**                 | Customer Page - Success Stories Section                                                                                                                     |
| **Deskripsi**             | Memverifikasi bahwa headline "Success Stories" terlihat di halaman Customer                                                                                 |
| **Langkah Uji Coba**      | 1. Buka halaman Customer (https://www.glpi-project.org/customers/)<br>2. Cari elemen headline "Success Stories"<br>3. Verifikasi visibility elemen tersebut |
| **Data Uji Coba**         | - URL: https://www.glpi-project.org/customers/<br>- Selector: Headline "Success Stories"                                                                    |
| **Hasil yang Diharapkan** | Headline "Success Stories" ditampilkan dan visible di halaman                                                                                               |
| **ID Bug**                | -                                                                                                                                                           |
| **Nama Tester**           | [Nama Tester]                                                                                                                                               |
| **Tanggal Eksekusi**      | 7 Januari 2026                                                                                                                                              |
| **Status**                | ⬜ Belum Dijalankan / ✅ Berhasil / ❌ Gagal                                                                                                                |

---

## Test Case 2: Verify Success Video Thumbnail

| **Field**                 | **Detail**                                                                                                                                                               |
| ------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| **Test Case ID**          | TC-CUST-002                                                                                                                                                              |
| **Fitur**                 | Customer Page - Success Video                                                                                                                                            |
| **Deskripsi**             | Memverifikasi bahwa thumbnail video success story terlihat di halaman                                                                                                    |
| **Langkah Uji Coba**      | 1. Buka halaman Customer (https://www.glpi-project.org/customers/)<br>2. Cari elemen video thumbnail di section Success Stories<br>3. Verifikasi visibility elemen video |
| **Data Uji Coba**         | - URL: https://www.glpi-project.org/customers/<br>- Selector: Video thumbnail element                                                                                    |
| **Hasil yang Diharapkan** | Thumbnail video success story ditampilkan dan visible di halaman                                                                                                         |
| **ID Bug**                | -                                                                                                                                                                        |
| **Nama Tester**           | [Nama Tester]                                                                                                                                                            |
| **Tanggal Eksekusi**      | 7 Januari 2026                                                                                                                                                           |
| **Status**                | ⬜ Belum Dijalankan / ✅ Berhasil / ❌ Gagal                                                                                                                             |

---

## Test Case 3: Click Success Video and Verify Lightbox

| **Field**                 | **Detail**                                                                                                                                                                        |
| ------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Test Case ID**          | TC-CUST-003                                                                                                                                                                       |
| **Fitur**                 | Customer Page - Video Player Interaction                                                                                                                                          |
| **Deskripsi**             | Memverifikasi bahwa ketika video thumbnail diklik, lightbox video player muncul                                                                                                   |
| **Langkah Uji Coba**      | 1. Buka halaman Customer (https://www.glpi-project.org/customers/)<br>2. Scroll ke section Success Stories<br>3. Klik pada thumbnail video<br>4. Verifikasi lightbox video muncul |
| **Data Uji Coba**         | - URL: https://www.glpi-project.org/customers/<br>- Action: Click video thumbnail<br>- Expected Element: Video lightbox/modal                                                     |
| **Hasil yang Diharapkan** | - Video thumbnail dapat diklik<br>- Lightbox video player muncul setelah diklik<br>- Video dapat diputar di dalam lightbox                                                        |
| **ID Bug**                | -                                                                                                                                                                                 |
| **Nama Tester**           | [Nama Tester]                                                                                                                                                                     |
| **Tanggal Eksekusi**      | 7 Januari 2026                                                                                                                                                                    |
| **Status**                | ⬜ Belum Dijalankan / ✅ Berhasil / ❌ Gagal                                                                                                                                      |

---

## Test Case 4: Verify Success Video Data Source

| **Field**                 | **Detail**                                                                                                                                                                                                 |
| ------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Test Case ID**          | TC-CUST-004                                                                                                                                                                                                |
| **Fitur**                 | Customer Page - Video Source Validation                                                                                                                                                                    |
| **Deskripsi**             | Memverifikasi bahwa video memiliki data-src yang benar dengan video ID yang sesuai                                                                                                                         |
| **Langkah Uji Coba**      | 1. Buka halaman Customer (https://www.glpi-project.org/customers/)<br>2. Inspect elemen video thumbnail<br>3. Ambil value dari attribute data-src<br>4. Verifikasi data-src mengandung video ID yang benar |
| **Data Uji Coba**         | - URL: https://www.glpi-project.org/customers/<br>- Expected Video ID: yNnYSwF7Xxo<br>- Attribute: data-src                                                                                                |
| **Hasil yang Diharapkan** | - Attribute data-src ditemukan pada elemen video<br>- Value data-src mengandung video ID "yNnYSwF7Xxo"<br>- Video source valid dan dapat diload                                                            |
| **ID Bug**                | -                                                                                                                                                                                                          |
| **Nama Tester**           | [Nama Tester]                                                                                                                                                                                              |
| **Tanggal Eksekusi**      | 7 Januari 2026                                                                                                                                                                                             |
| **Status**                | ⬜ Belum Dijalankan / ✅ Berhasil / ❌ Gagal                                                                                                                                                               |

---

## Test Case 5: Click Watch YouTube Channel Button

| **Field**                 | **Detail**                                                                                                                                                                                                                     |
| ------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| **Test Case ID**          | TC-CUST-005                                                                                                                                                                                                                    |
| **Fitur**                 | Customer Page - YouTube Channel Redirect                                                                                                                                                                                       |
| **Deskripsi**             | Memverifikasi bahwa tombol "Watch our Youtube Channel" mengarahkan user ke channel YouTube GLPI Network yang benar                                                                                                             |
| **Langkah Uji Coba**      | 1. Buka halaman Customer (https://www.glpi-project.org/customers/)<br>2. Cari tombol "Watch our Youtube Channel"<br>3. Klik tombol tersebut<br>4. Tunggu tab baru terbuka<br>5. Switch ke tab baru<br>6. Verifikasi URL tujuan |
| **Data Uji Coba**         | - URL Asal: https://www.glpi-project.org/customers/<br>- Action: Click button "Watch our Youtube Channel"<br>- Expected URL: https://www.youtube.com/@glpi-network                                                             |
| **Hasil yang Diharapkan** | - Tombol "Watch our Youtube Channel" dapat diklik<br>- Tab baru terbuka setelah klik<br>- URL yang dibuka adalah https://www.youtube.com/@glpi-network<br>- YouTube channel GLPI Network ditampilkan dengan benar              |
| **ID Bug**                | -                                                                                                                                                                                                                              |
| **Nama Tester**           | [Nama Tester]                                                                                                                                                                                                                  |
| **Tanggal Eksekusi**      | 7 Januari 2026                                                                                                                                                                                                                 |
| **Status**                | ⬜ Belum Dijalankan / ✅ Berhasil / ❌ Gagal                                                                                                                                                                                   |

---

## Summary Hasil Testing

| **Metric**                | **Value** |
| ------------------------- | --------- |
| **Total Test Cases**      | 5         |
| **Berhasil (✅)**         | -         |
| **Gagal (❌)**            | -         |
| **Belum Dijalankan (⬜)** | 5         |
| **Pass Rate**             | 0%        |

---

## Catatan Tambahan

### Prerequisites

- Browser: Chrome 143.0.7499.170
- ChromeDriver: Compatible version
- Internet connection: Required
- Page Load: Wait for complete page load before testing

### Known Issues

- (Akan diisi setelah eksekusi test)

### Rekomendasi

- (Akan diisi setelah eksekusi test)

---

**Template Pengisian Status:**

- ⬜ Belum Dijalankan: Test belum dieksekusi
- ✅ Berhasil: Test passed, hasil sesuai ekspektasi
- ❌ Gagal: Test failed, catat ID Bug dan detail error
