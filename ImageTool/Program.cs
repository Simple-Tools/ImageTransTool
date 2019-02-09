using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImageTool
{
    class Program
    {
        private static string path = "./";
        private static string compressDestFloder = "../compress/";
        private static string encryptDestFloder = "../encrypt/";

        static void Main(string[] args)
        {
            ImageCompress();
            ImageEncrpy();

        }
        private static void ImageCompress()
        {
            if (!Directory.Exists(compressDestFloder))
            {
                Directory.CreateDirectory(compressDestFloder);
            }
            var files = Directory.GetFiles(path).ToList();
            var imgFiles = files.FindAll(s => s.EndsWith(".JPG"));
            foreach (var img in imgFiles)
            {
                ImageCompressUtility.CompressImage(img, compressDestFloder + img, 50);
            }
        }

        private static void ImageEncrpy()
        {
            if (!Directory.Exists(encryptDestFloder))
            {
                Directory.CreateDirectory(encryptDestFloder);
            }
            var files = Directory.GetFiles(compressDestFloder)
                            .ToList().FindAll(s => s.EndsWith(".JPG"));
            foreach (var img in files)
            {
                var imgName = img.Substring(compressDestFloder.LastIndexOf("/")+1);
                var destImg = encryptDestFloder + imgName.Substring(0,imgName.Length-4);
                ImageEncryptUtitlity.EncryptFile(img, destImg + ".ENC.JPG", "password");
            }
        }
    }
}
