class String
  def is_integer?
    self.to_i.to_s == self
  end
end


def get_all_videos
  Video.all
end

def current_user
  session[:user]
end

def get_video_url(id)
  Video.find(id).url
end

def new_video(title, url, week_number, semester_number, is_beginner, is_advanced)
  Video.create(title: title, url: url, week_number: week_number, semester_number: semester_number, is_beginner: is_beginner, is_advanced: is_advanced)
end

def all_videos_for_semester(level, semester)
  if level == 1 && semester == 1
    Video.where(is_beginner: true)
  elsif level == 2 && semester ==1
    Video.where(is_advanced: true)
  end
end

def search_videos(options = {})
  key = options.first[0].to_s
  value = options.first[1]
  value = value.to_i if value.is_integer?
  @result = Video.where(key => value)
  return @result
end

def delete_vid(id)
  Video.find(id).destroy
end

# Homepage (Root path)
get '/' do
  erb :login
end

get '/yolo' do
  "yolo"
end

get '/main_view' do
  if current_user
    @user = session[:user]
    @videos = Video.all
    erb :'main_view'
  else
    redirect '/permission_denied'
  end
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

delete '/videos/:id' do
  delete_vid(params[:id])
end

get '/videos/:level/:semester_number' do
  content_type :json
  all_videos_for_semester(params[:level].to_i, params[:semester_number].to_i).to_json
end



get '/videos/:id/view' do
  "yolo"
end

post '/videos/' do
  @title = params[:title]
  @url = params[:url]
  @week_number = params[:week_number]
  @semester_number = params[:semester_number]
  @is_beginner = params[:is_beginner]
  @is_advanced = params[:is_advanced]
  new_video(@title, @url, @week_number, @semester_number, @is_beginner, @is_advanced)
end

post '/verify_login' do
  @user = User.find_by email: params[:email]
  if @user && @user.authenticate(params[:password])
    session[:user] = @user
    if(@user.is_admin?)
      redirect '/admin_controller'
    else
      redirect '/main_view'
    end
  else
    redirect '/authentification_failed'
  end
end

get '/authentification_failed' do
  "ERROR WRONG PASSWORD"
end

get '/admin_controller' do
  @user = current_user
  if @user.is_admin?
    erb :admin_controller
  else
    redirect '/authentification_failed'
  end
end

get '/videos/search/' do
  content_type :json

  @week_number = params[:week_number]
  @title = params[:title]
  @url = params[:url]
  @is_beginner = params[:is_beginner]
  @is_advanced = params[:is_advanced]
  @id = params[:id]
  @semester_number = params[:semester_number]

  @options = {semester_number: @semester_number, week_number: @week_number, title: @title, url: @url, is_beginner: @is_beginner, is_advanced: @is_advanced, id: @id}
  @options.delete_if { |key, value| value.nil? }
  search_videos(@options).to_json
end
