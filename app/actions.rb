def get_all_videos
  Video.all
end

def get_video_url(id)
  Video.find(id).url
end

def new_video(title, url)
  Video.create(title: title, url: url)
end

# Homepage (Root path)
get '/' do
  erb :index
end

get '/yolo' do
  "yolo"
end

get '/mainview' do
  @videos = get_all_videos
  erb :main_view
end

get '/videos' do
  content_type :json
  #respond_with :bookmark_list, @bookmarks
  get_all_videos.to_json
end

get '/videos/:id/url' do
  content_type :json
  get_video_url(params[:id])
end

get '/videos/:id/view' do
  "yolo"
end

post '/videos' do
  @title = params[:title]
  @url = params[:url]
  new_video(@title, @url)
end

get '/login' do
  erb :login
end
