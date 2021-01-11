db = db.getSiblingDB('kemenudb'); // like 'use kemenudb'
db.createCollection('admin');
db.admin.createIndex({'email': 1}, {unique: true});