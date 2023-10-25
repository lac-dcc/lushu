from bs4 import BeautifulSoup
import re
import sys
import io

def extract_script_urls(html_content):
    urls = []

    soup = BeautifulSoup(html_content, 'html.parser')

    script_tags = soup.find_all('script')
    print(script_tags)
    for script_tag in script_tags:
        if script_tag.string:
            urls.extend(re.findall(r'"(http[s]?://.*?)"', script_tag.string))

    return urls

# Check if an HTML file was passed as a command-line argument
if len(sys.argv) < 2:
    print("Usage: python script.py input_file.html")
    sys.exit(1)

# Read the content of the HTML file
file_path = sys.argv[1]
try:
    with io.open(file_path, 'r', encoding='utf-8') as file:
        input_html = file.read()
except IOError:
    print("Error opening file:", file_path)
    sys.exit(1)

urls = extract_script_urls(input_html)

print("Extracted URLs:")
for url in urls:
    print(url)
