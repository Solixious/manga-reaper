import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.net.*;
import java.util.*;

class MangaReaper {
	private static final String PREFIX = "http://www.mangareader.net/";
	private static final String USER_HOME = "user.home";
	private static final String DOWNLOADS = "/Downloads/";
	
	private String name;
	private String url;
	
	public int checkExistence(String name) throws IOException, MalformedURLException {
		name = name.toLowerCase();
		name = name.replace(" ", "-");
		url = PREFIX + name;
		System.out.println(url);
		HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
		con.setRequestMethod("HEAD");
		con.connect();
		int s = con.getResponseCode();
		System.out.println(s);
		this.name=name;
		return s;
	}

	public void downloadManga() throws Exception {
		String nextURL = this.url + "/1";
		while(nextURL != null) {
			URL url = new URL(nextURL);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			String html = br.readLine();
			boolean flag =  true;
			while(html != null && flag) {
				html = br.readLine();
				int in = html.indexOf("imgholder");
				if(in != -1) {
					while(html != null && flag) {
						int start = html.indexOf("src=", in) + 5;
						int end = html.indexOf("\"", start);
					
						if(start - 5 != -1) {
							String imgUrl = html.substring(start, end);

							start = html.indexOf("alt=", start) + 5;
							end = html.indexOf("\"", start);
							String fileName = System.getProperty(USER_HOME) + DOWNLOADS + name + "/" + html.substring(start, end).replace(" - ", "/") + ".jpg";
							downloadImage(imgUrl, fileName);
							flag = false;
							System.out.println("Start: " + start + "\nEnd: " + end);
							System.out.println("HTML: " + html);
							System.out.println("Image URL: " + imgUrl);
							System.out.println("File Name: " + fileName);
						}
						start = html.indexOf("a href=", in) + 9;
						end = html.indexOf("\"", start);
						if(start - 9 != -1) {
							nextURL = PREFIX + html.substring(start, end);
							System.out.println("Next URL: " + nextURL); 
						}
						in = 0;
						html = br.readLine();
					}
				}
			}
		
			br.close();
		}
	}
	private void downloadImage(String url, String path) throws Exception {
		File file = new File(path);
		file.getParentFile().mkdirs();
		URL website = new URL(url);
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream(file);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		rbc.close();
		fos.close();
	}
}
