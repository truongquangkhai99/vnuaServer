import requests
from bs4 import BeautifulSoup

# letters = 'anhtd' #for test
letters = 'abcdefghijklmnopqrstuvwxyz'


f= open("teacher.txt","w+")
for a in letters:
    for b in letters:
        for c in letters:
            for d in range(1, 100):                
                id = f'{a}{b}{c}{d:02}'
                url = f'http://daotao.vnua.edu.vn/default.aspx?page=thoikhoabieu&sta=1&id={id}'
                
                print(f'\n{url}')
                page = requests.get(url)
                soup = BeautifulSoup(page.content, 'html.parser')                
                nameElement = soup.find(id="ctl00_ContentPlaceHolder1_ctl00_lblContentTenSV")
                if nameElement:
                    name = nameElement.getText()
                    print('\n' + name)
                    f.write(f"\n{id},{name}")
                    f.flush()
                else:
                    break

f.close() 