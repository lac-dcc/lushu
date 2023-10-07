from bs4 import BeautifulSoup
import re
import sys

def extract_script_urls(html_content):
    urls = []

    soup = BeautifulSoup(html_content, 'html.parser')

    script_tags = soup.find_all('script')

    regex = re.compile('(https?:\/\/)?([a-zA-Z0-9\.-]+\.[a-z\.]{2,6})([\/\w \.-]*)*\/?')
    for script_tag in script_tags:
        if script_tag.string:
            urls_found = [''.join(r) for r in regex.findall(script_tag.string)]
            urls.extend(urls_found)

    return urls

def main():
    # Check if an HTML file was passed as a command-line argument
    if len(sys.argv) < 2:
        print("Usage: python script.py input_file.html")
        return

    # Read the content of the HTML file
    file_path = sys.argv[1]
    input_html = open(file_path).read()

    urls = extract_script_urls(input_html)

    print("Extracted URLs:")
    for url in urls:
        print(url)

if __name__ == "__main__":
    main()
