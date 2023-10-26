from bs4 import BeautifulSoup
import timeit
import psutil
import os
import re
import sys
import io

def extract_script_urls(html_content):
    urls = []

    soup = BeautifulSoup(html_content, 'html.parser')

    script_tags = soup.find_all('script')
    for script_tag in script_tags:
        urls.extend(re.findall(r"[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z]+", str(script_tag)))
    

    return urls

# Check if an HTML file was passed as a command-line argument
if len(sys.argv) < 3:
    print("Usage: python script.py input_file.txt out_file.txt")
    sys.exit(1)
    
# Read the content of the HTML file
file_path = sys.argv[1]

try:
    with io.open(file_path, 'r', encoding='utf-8') as file:
        input_html = file.read()
except IOError:
    print("Error opening file:", file_path)
    sys.exit(1)

start_time = timeit.default_timer()
process = psutil.Process(os.getpid())
memory_before = process.memory_info().rss
urls = extract_script_urls(input_html)
memory_after = process.memory_info().rss
end_time = timeit.default_timer()

print(end_time - start_time)
print(memory_after - memory_before)

output_path = sys.argv[2]
try:
    with io.open(output_path, 'w', encoding='utf-8') as outfile:
        for url in urls:
            outfile.write(url + "\n")
        outfile.close()
except IOError:
    print("Error opening file:", output_path)