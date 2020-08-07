import json

ctgrTitle = ["가공식품류", "건어물류", "곡류", "과일류", "기타", "달걀_유제품", "닭고기", "돼지고기", "밀가루", "버섯류", "소고기", "쌀", "육류", "채소류", "콩_견과류", "해물류"]


newData = dict()
for fileName in ctgrTitle:
    with open("Recipe/" + fileName + ".json", 'r', encoding = "UTF-8") as json_file:
        data = json.load(json_file)
        for recipeTitle, recipeContent in data.items():
            newTitle = ""
            for letter in recipeTitle:
                if ord(letter) >= int("AC00", 16) and ord(letter) <= int("D7AF", 16):
                    newTitle += letter
            recipeContent["recipeKey"] = newTitle
            newData[newTitle] = recipeContent
            
    
    with open("Recipe/" + fileName + ".json", 'w', encoding = "UTF-8") as json_file:
        json.dump(newData, json_file, indent="\t", ensure_ascii = False)