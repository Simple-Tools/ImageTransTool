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
        static void Main(string[] args)
        {
            var path = "./";
            var destFloder = "../compress/";
            if (!Directory.Exists(destFloder))
            {
                Directory.CreateDirectory(destFloder);
            }
            var files = Directory.GetFiles(path).ToList();
            var imgFiles = files.FindAll(s => s.EndsWith(".JPG"));
            foreach (var img in imgFiles)
            {
                ImageCompressUtility.CompressImage(img, destFloder + img, 50);
            }
        }
    }
}
