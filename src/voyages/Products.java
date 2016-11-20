package voyages;



import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;

import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

import exceptions.ProductException;

/**
 * Servlet implementation class Products
 */
@WebServlet("/products/")
public class Products extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public static ArrayList<Product> getProducts(ArrayList<Integer> codes) throws ParserConfigurationException, SAXException, IOException, ProductException {
        ArrayList<Product> products = Product.getProducts();
        ArrayList<Product> result = new ArrayList<Product>();
        
        for(int i =0; i < products.size(); i++) {
            Product product = products.get(i);
            if(codes.indexOf(product.getCode()) != -1)
                result.add(product);
        }
        
        return result;
    }
    public static Product getProduct(int code) throws ProductException {
        ArrayList<Product> products;
        
        products = Product.getProducts();
        
        Product found = null;
        for(int i =0; i < products.size(); i++) {
            if(products.get(i).getCode() == code) {
                found = products.get(i);
            }
        }
        return found;
    }
    public static ArrayList<Product> getProducts() throws ProductException {
        String filepath = "products.xml";
        System.out.println("Path : ");
        
        //System.out.println(this.getClass().getResource(".").getPath());
        ClassLoader classLoader = Product.class.getClassLoader();
        
        
         File file = new File(classLoader.getResource(filepath).getFile());
        //new File(source);
        //File fXmlFile = new File(file);
         
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        
        DocumentBuilder dBuilder;
        Document doc = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(file);
        } catch (IOException | SAXException | ParserConfigurationException e) {
            throw new ProductException(e);
        }
        
        doc.getDocumentElement().normalize();
    
        NodeList nList = doc.getElementsByTagName("product");
        ArrayList<Product> products = new ArrayList<Product>();
        
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                Product product = new Product();
                product.setNom(getChildText(eElement, "nom"));
                product.setCode(Integer.parseInt(getChildText(eElement, "code")));
                product.setDescription(getChildText(eElement, "description"));
                product.setImage(getChildText(eElement, "image"));
                product.setPrice(Double.parseDouble(getChildText(eElement, "price")));
                
                products.add(product);
            }
        }
        return products;
    }
    public byte[] getImageAsBytes() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
            InputStream in = Product.class.getResourceAsStream("/images/"+this.getImage());
            byte[] buffer = new byte[4096];
            for (;;) {
                int nread = in.read(buffer);
                if (nread <= 0) {
                    break;
                }
                baos.write(buffer, 0, nread);
            }
            byte[] data = baos.toByteArray();
            String text = new String(data, "Windows-1252");
            byte[] asByteObjects = new byte[data.length];
            for (int i = 0; i < data.length; ++i) {
                asByteObjects[i] = data[i];
            }
            return asByteObjects;
    }
    public static String encode(byte[] data)
        {
            char[] tbl = {
                'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P',
                'Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f',
                'g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v',
                'w','x','y','z','0','1','2','3','4','5','6','7','8','9','+','/' };

            StringBuilder buffer = new StringBuilder();
            int pad = 0;
            for (int i = 0; i < data.length; i += 3) {

                int b = ((data[i] & 0xFF) << 16) & 0xFFFFFF;
                if (i + 1 < data.length) {
                    b |= (data[i+1] & 0xFF) << 8;
                } else {
                    pad++;
                }
                if (i + 2 < data.length) {
                    b |= (data[i+2] & 0xFF);
                } else {
                    pad++;
                }

                for (int j = 0; j < 4 - pad; j++) {
                    int c = (b & 0xFC0000) >> 18;
                    buffer.append(tbl[c]);
                    b <<= 6;
                }
            }
            for (int j = 0; j < pad; j++) {
                buffer.append("=");
            }

            return buffer.toString();
        }
    public String getImageAsBase64() throws IOException {
        byte[] imgData = this.getImageAsBytes();
        String imgDataBase64=new String(encode(imgData));
        return imgDataBase64;
    }
    

    
    private static String getChildText(Element parent, String tagName) {
        return parent.getElementsByTagName(tagName).item(0).getTextContent();
    }
    
    private int id = 0;
    
    private String content="";

    private String description="";

    private String image="";

    private int code;

    private String nom;
    
    private double price;

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public int getCode ()
    {
        return code;
    }
    public double getPrix ()
    {
        return price;
    }
    public double getPrice ()
    {
        return price;
    }
    public void setPrice (double price)
    {
        this.price = price;
    }
    public void setCode (int code)
    {
        this.code = code;
    }

    public String getNom ()
    {
        return nom;
    }

    public void setNom (String nom)
    {
        this.nom = nom;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [content = "+content+", description = "+description+", image = "+image+", code = "+code+", nom = "+nom+"]";
    }
}
