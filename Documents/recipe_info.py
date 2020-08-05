# -*- coding: utf-8 -*-
"""
Created on Sun Aug  2 21:03:59 2020

@author: sungh
"""


import requests
from bs4 import BeautifulSoup
import json
import os

url='https://www.10000recipe.com'

def move_page(link):
    response = requests.get(link)
    soup = BeautifulSoup(response.text,'html.parser')
    a= None
    addr=""
    for ul in soup.select('ul.pagination'):
       a = ul.select('a')
    for i in a:
        this_=i.get('href')
        addr=this_
        print(addr)
        get_recipe_list(url+this_)
    


def get_recipe_list(link):
    response=requests.get(link)
    soup=BeautifulSoup(response.text,"html.parser")
    aa=soup.find_all('a','common_sp_link')
    i=0
    for a in aa:
        ch=a.get('href')
        get_info(url+ch)
        i+=1
    print(i)
    
def get_info(link):
    file_path='D:\contest\projectTemp\Documents/소고기.json'
    temp_dict={}
    raw=requests.get(link)
    soup=BeautifulSoup(raw.text,"html.parser")
    #제목
    container_t =soup.find('div','view2_summary')
    title=container_t.find('h3').get_text()
    #타이틀 이미지
    title_c=soup.find('div','centeredcrop')
    title_image=title_c.find('img').get('src')
    #재료
    container_i=soup.find('div','ready_ingre3')
    ingredient={}
    try:
        for i in container_i.find_all('ul'):
            source=[]
            a=i.find('b').get_text().replace('[',"").replace(']','')
            for tmp in i.find_all('li'):
                source.append(tmp.get_text().replace('\n','').replace(' ',''))
                ingredient[a]=source
    except(AttributeError):
        return 
    #레시피
    recipe={}
    recipe_image={}
    container_r=soup.find('div','view_step')
    i=0
    for n in container_r.find_all('div','view_step_cont'):
        i+=1
        recipe[i]=n.get_text().replace('\n','')
        if n.find('img') == None:
            recipe_image={}
        else:
            recipe_image[i]=n.find('img').get('src')
    if not recipe:
        return
    temp_dict[title]={'title':title,'titleImage':title_image,"ingredient":ingredient,"recipe":recipe,'recipeImage':recipe_image}
    if not os.path.isfile(file_path):
            with open(file_path,'w',encoding='utf-8') as f:
                json.dump(temp_dict,f,ensure_ascii=False,indent='\t')
    else:
        with open(file_path,'r',encoding='utf-8') as s:
            data=json.load(s)
        data.update(temp_dict)
        with open(file_path,'w',encoding='utf-8') as f:
            json.dump(data,f,ensure_ascii=False,indent='\t')
        f.close()
    

move_page('https://www.10000recipe.com/recipe/list.html?cat3=70&order=reco&page=1')    






