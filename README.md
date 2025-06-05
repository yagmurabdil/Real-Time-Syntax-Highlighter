ColorParse - Real-Time Grammar-Based Syntax Highlighter 

Bu proje, Java dersi için geliştirilmiş **gerçek zamanlı syntax highlighting** özellikli bir kod editörüdür. Proje, formal grammar tabanlı lexical analysis ve syntax parsing kullanarak Java kodlarını renkli olarak gösterir.

Proje Gereksinimleri

1. Lexical Analysis (Kelime Analizi)
- **Regex tabanlı tokenizer** - Gelişmiş pattern matching
- **14 farklı token türü** (5+ gereksinimi aşıldı):
  - KEYWORD (anahtar kelimeler)
  - TYPE (veri türleri)
  - STRING (string literaller)
  - COMMENT (yorumlar)
  - NUMBER (sayılar)
  - OPERATOR (operatörler)
  - DELIMITER (ayırıcılar)
  - IDENTIFIER (tanımlayıcılar)
  - LITERAL (true, false, null)
  - ANNOTATION (@Override, @Deprecated)
  - PREPROCESSOR (import, package)
  - ERROR (hatalı tokenlar)
  - WHITESPACE (boşluklar)
  - EOF (dosya sonu)

2. Syntax Analysis (Sözdizimi Analizi)
- **Formal grammar kuralları** uygulandı
- **Context-free grammar** tabanlı parsing
- **Hata tespiti ve raporlama**
- **Parse tree** oluşturma (basitleştirilmiş)

3. Real-Time Highlighting (Gerçek Zamanlı Renklendirme)
- **500ms optimized delay** - Performans için optimize edildi
- **5+ token türü renklendirme** (14 farklı renk)
- **Rate limiting** - Aşırı yüklenmeyi önler
- **Visual Studio Code benzeri renk şeması**

4. GUI (Grafik Kullanıcı Arayüzü)
- **Modern Swing arayüzü** - 1000x700 pencere
- **Koyu tema** - Göz dostu editör
- **Status bar** - Durum bilgisi
- **Scroll desteği** - Uzun kodlar için
- **Consolas font** - Kod yazmaya uygun

5. Programming Language Support
- **Java odaklı** - Tam Java syntax desteği
- **50+ Java keyword** tanıma
- **Java türleri** (String, Integer, List, vb.)
- **Java operatörleri** (++, --, +=, ==, vb.)
- **Annotation desteği** (@Override, @Deprecated)

 Özellikler

Lexical Analysis Özellikleri:
- **Regex Pattern Matching** - Gelişmiş tokenization
- **Multi-line comment** desteği (/* */)
- **Single-line comment** desteği (//)
- **String escape** karakterleri
- **Hexadecimal sayılar** (0xFF)
- **Float sayılar** (3.14f, 2.5d)
- **Scientific notation** (1e10)

Syntax Analysis Özellikleri:
- **Package declaration** kontrolü
- **Import statement** doğrulama
- **Class/Interface/Enum** tanımları
- **Method signature** kontrolü
- **Parantez eşleştirme**
- **Modifier sequence** doğrulama

Real-Time Highlighting Özellikleri:
- **Token-based coloring** - Her token türü için özel renk
- **Performance optimization** - Rate limiting ile
- **Error highlighting** - Hatalı tokenlar kırmızı
- **Syntax error reporting** - Konsol çıktısı

Renk Şeması

| Token Türü | Renk | Açıklama |
|------------|------|----------|
| **Keywords** | 🔵 Mavi (#569CFF) | public, class, if, for |
| **Types** | 🔷 Cyan (#4EC9B0) | String, Integer, List |
| **Strings** | 🟠 Turuncu (#CE9178) | "Hello World" |
| **Comments** | 🟢 Yeşil (#6A9955) | // ve /* */ |
| **Numbers** | 🟢 Açık Yeşil (#B5CEA8) | 42, 3.14, 0xFF |
| **Literals** | 🔵 Mavi-Mor (#569CFF) | true, false, null |
| **Annotations** | 🟡 Sarı (#DCDC7A) | @Override |
| **Preprocessor** | 🟣 Mor (#C586C0) | import, package |
| **Operators** | ⚪ Gri (#D4D4D4) | +, -, ==, != |
| **Delimiters** | ⚪ Açık Gri (#D4D4D4) | ;, (, ), {, } |
| **Errors** | 🔴 Kırmızı | Tanınmayan tokenlar |

Nasıl Çalıştırılır?

### Gereksinimler:
- Java 8 veya üstü
- Windows/Linux/Mac desteği

### Çalıştırma:
```bash
# Derleme
javac -cp bin -d bin src/main/java/com/colorparse/*/*.java

# Çalıştırma
java -cp bin com.colorparse.app.ColorParseApp
```

## 🔧 Teknik Detaylar

### Lexical Analysis Algoritması:
1. **Regex Pattern Matching** - Her token türü için özel pattern
2. **Greedy Tokenization** - En uzun eşleşmeyi seç
3. **Context-aware Classification** - Keyword vs Identifier ayrımı
4. **Position Tracking** - Line/column bilgisi
5. **Error Recovery** - Bilinmeyen karakterleri ERROR token olarak işle

### Syntax Analysis Algoritması:
1. **Recursive Descent Parser** - Top-down parsing
2. **Grammar Rules** - Java syntax kuralları
3. **Error Reporting** - Line/column ile hata konumu
4. **Recovery Strategies** - Hata sonrası devam etme


1. ✅ **Lexical Analysis** - Regex tabanlı, 14 token türü
2. ✅ **Syntax Analysis** - Grammar tabanlı parser
3. ✅ **Real-time Highlighting** - 500ms optimized
4. ✅ **GUI** - Modern Swing arayüzü
5. ✅ **5+ Token Types** - 14 farklı renk

Bu proje, **Visual Studio Code** benzeri bir syntax highlighting deneyimi sunarak Java dilinde yazılan kodları anlık olarak renklendirmektedir.


