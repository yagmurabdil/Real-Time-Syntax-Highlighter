ColorParse - Real-Time Grammar-Based Syntax Highlighter 

Projenin detaylı Teknik Dökümanı ve Demo Youtube Videosuna Article.docx dosyasından ulaşabilirsiniz.

Bu proje, Java dersi için geliştirilmiş **gerçek zamanlı syntax highlighting** özellikli bir kod editörüdür. Proje, formal grammar tabanlı lexical analysis ve syntax parsing kullanarak Java kodlarını renkli olarak gösterir.

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

Bu proje, **Visual Studio Code** benzeri bir syntax highlighting deneyimi sunarak Java dilinde yazılan kodları anlık olarak renklendirmektedir.


