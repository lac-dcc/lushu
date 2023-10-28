from bs4 import BeautifulSoup
import timeit
import psutil
import os
import gc
import re
import sys
import io

def get_process_memory():
    process = psutil.Process(os.getpid())
    return process.memory_info().rss

def extract_script_urls(html_content):
    urls = []

    soup = BeautifulSoup(html_content, 'html.parser')

    script_tags = soup.find_all('div')
    for script_tag in script_tags:
        urls.extend(re.findall(r"[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z]+", str(script_tag)))
    
    return set(urls)

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
gc.collect()
mem_before = get_process_memory()

start_time = timeit.default_timer()

urls = extract_script_urls(input_html)
output_path = sys.argv[2]
try:
    with open(output_path, 'w', encoding='utf-8') as outfile:
        for url in urls:
            outfile.write(url + "\n")
        outfile.close()
except IOError:
    print("Error opening file:", output_path)

end_time = timeit.default_timer()

gc.collect()
mem_after = get_process_memory()
used_memory = mem_after - mem_before


print((end_time - start_time)*1000)
print(used_memory/1048576.0)

