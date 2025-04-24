# Repsy – Lightweight Private Package Repository

**Repsy**, Spring Boot tabanlı, hafif ve esnek bir özel (private) paket depolama ve dağıtım servisidir. Paketlerinizi yerel dosya sistemi veya MinIO (S3 uyumlu) nesne depolama üzerinde tutar ve basit bir REST API ile yönetmenizi sağlar.

## Genel Özellikler

- Kolayca Docker Compose ile çalıştırılabilir yapı
- Seçilebilir depolama stratejileri:
  - **Yerel dosya sistemi**
  - **MinIO (S3 uyumlu nesne depolama)**
- Basit ve kullanımı kolay REST API:
  - Paket yükleme (Upload)
  - Paket indirme (Download)
  - Paket sürüm listeleme (Metadata)

## Ön Koşullar

| Teknoloji | Tavsiye Edilen Sürüm |
|-----------|----------------------|
| Java      | 17 (LTS)             |
| Maven     | ≥ 3.8                |
| Docker    | ≥ 24                 |
| Docker Compose | v2              |

## Projenin Kurulumu ve Çalıştırılması

Projeyi çalıştırmak için aşağıdaki adımları uygulayın:

**1\. Repoyu klonlayın:**

```bash
git clone <repo-url>
cd repsy-repo
```

**2\. Maven ile uygulamayı derleyin:**

```bash
mvn clean verify -DskipTests
```

**3\. Docker Compose ile uygulamayı başlatın:**

```bash
docker compose up --build
```

| Servis       | Port                             | Açıklama                    |
|--------------|----------------------------------|-----------------------------|
| repo-app     | `8080`                           | REST API (Spring Boot)      |
| PostgreSQL   | `5432`                           | Metadata için veritabanı    |
| MinIO        | `9000` (API), `9001` (Web Arayüz)| Paket depolama              |

> MinIO yönetim arayüzüne erişim:
> - URL: `http://localhost:9001`
> - Kullanıcı adı: `minio`
> - Şifre: `minio123`

## API Kullanımı

API Temel URL’i:
```
http://localhost:8080/api/packages
```

| HTTP Metodu | Endpoint             | Açıklama                   |
|-------------|----------------------|----------------------------|
| `PUT`       | `/pkg/{version}`     | Paket yükleme (upload)     |
| `GET`       | `/pkg/{version}`     | Paket indirme (download)   |
| `GET`       | `/pkg`               | Paketin sürümlerini listele|
| `DELETE`    | `/pkg/{version}`     | Paket sürümü sil (opsiyonel)|

### Örnek Kullanım

**Paket Yükleme:**
```bash
curl -X PUT --data-binary @build/package.rep \
  http://localhost:8080/api/packages/demo/1.0.0
```

**Paket İndirme:**
```bash
curl -o demo-1.0.0.rep \
  http://localhost:8080/api/packages/demo/1.0.0
```

## Otomatik Testlerin Çalıştırılması

API’nin temel fonksiyonlarını doğrulamak için test betiğini çalıştırabilirsiniz:

```bash
./scripts/test_api.sh
```

Bu script aşağıdaki adımları gerçekleştirir:

- **Deploy:** Paketi yükler
- **Download:** Paketi indirir ve doğrular
- **Negatif Test:** Hatalı isteklerin yönetildiğini kontrol eder

Başarılı test sonucu aşağıdaki gibidir:
```
Tüm testler geçti!
```

## Proje Sürümleme ve Dağıtımı

Mevcut sürüm: `1.0.0`

Git üzerinden yeni sürüm yayınlamak için:

```bash
git tag -a v1.0.0 -m "İlk stabil sürüm"
git push origin main --tags
```

Docker Hub'a push etmek için:

```bash
docker login
docker push <dockerhub-kullanici-adi>/repsy-repo-app:1.0.0
```

## Katkıda Bulunma

Projeyle ilgili geri bildirim, geliştirme önerileri veya hata raporları için lütfen bir Issue veya Pull Request oluşturun. Katkılarınız bizim için önemlidir.
