# district-demo-graphql

Simple demo project demonstrating capabilities of district0x GraphQL modules: [district-ui-graphql](https://github.com/district0x/district-ui-graphql). 
and [district-server-graphql](https://github.com/district0x/district-server-graphql). 

Please refer to [district-ui-graphql](https://github.com/district0x/district-ui-graphql) readme for more detailed 
introduction. 

## Development
```bash
lein deps

# Start Server
lein repl
(start-server!)
node server/district-demo-graphql.js

# Start UI
lein repl
(start-ui!)
# visit http://localhost:4629/
```